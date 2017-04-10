package control;

import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.PlaybackEvent;

class Mp3Player {
	
	private Bitstream bitstream;
	private Decoder decoder;
	private AudioDevice audio;
	
	private boolean closed;
	private boolean isPlaying;
	private boolean complete;
	private int lastPosition;
	
	
	public Bitstream getBitstream() {
		return bitstream;
	}

	public void setBitstream(Bitstream bitstream) {
		this.bitstream = bitstream;
	}

	public Decoder getDecoder() {
		return decoder;
	}

	public void setDecoder(Decoder decoder) {
		this.decoder = decoder;
	}

	public AudioDevice getAudio() {
		return audio;
	}

	public void setAudio(AudioDevice audio) {
		this.audio = audio;
	}
	
	/**
	 * Creates a new <code>Player</code> instance.
	 */
	public Mp3Player(InputStream stream) throws JavaLayerException
	{
		this(stream, null);
	}

	public Mp3Player(InputStream stream, AudioDevice device) throws JavaLayerException
	{
		bitstream = new Bitstream(stream);
		isPlaying = false;

		if (device!=null) audio = device;
		else audio = FactoryRegistry.systemRegistry().createAudioDevice();
		audio.open(decoder = new Decoder());
	}
	
	public void play() throws JavaLayerException
	{
		play(Integer.MAX_VALUE);
	}
	public boolean play(int frames) throws JavaLayerException
	{
		boolean ret = false;
		if(!isPlaying)
		{
			ret = true;
			isPlaying = true;
			// report to listener
			//if(listener != null) listener.playbackStarted(createEvent(PlaybackEvent.STARTED));
	
			while (frames-- > 0 && ret)
			{
				ret = decodeFrame();
			}
	
			if (!ret)
			{
				// last frame, ensure all data flushed to the audio device.
				AudioDevice out = audio;
				if (out != null)
				{
					System.out.println(audio.getPosition());
					out.flush();
	//				System.out.println(audio.getPosition());
					synchronized (this)
					{
						complete = (!closed);
						close();
					}
	
					// report to listener
				//	if(listener != null) listener.playbackFinished(createEvent(out, PlaybackEvent.STOPPED));
				}
			}
		}
		return ret;
	}
	
	/**
	 * Decodes a single frame.
	 *
	 * @return true if there are no more frames to decode, false otherwise.
	 */
	protected boolean decodeFrame() throws JavaLayerException
	{
		try
		{
			AudioDevice out = audio;
			if (out == null) return false;

			Header h = bitstream.readFrame();
			if (h == null) return false;

			// sample buffer set when decoder constructed
			SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h, bitstream);

			synchronized (this)
			{
				out = audio;
				if(out != null)
				{
					out.write(output.getBuffer(), 0, output.getBufferLength());
				}
			}

			bitstream.closeFrame();
		}
		catch (RuntimeException ex)
		{
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		return true;
	}
	
	/**
	 * Closes this player. Any audio currently playing is stopped
	 * immediately.
	 */
	public synchronized void close()
	{
		AudioDevice out = audio;
		if (out != null)
		{
			//closed = true;
			audio = null;
			// this may fail, so ensure object state is set up before
			// calling this method.
			out.close();
			lastPosition = out.getPosition();
			try
			{
				bitstream.close();
			}
			catch (BitstreamException ex)
			{}
		}
	}
}
