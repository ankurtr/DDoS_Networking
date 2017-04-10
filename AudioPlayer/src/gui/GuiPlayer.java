/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.Controller;

/**
 * @author Zykoq
 * 
 */
public class GuiPlayer extends JFrame implements ChangeListener {

    private static final long serialVersionUID = -1198425459975778311L;
    private JSlider slider;

    public GuiPlayer() {
        setTitle("Mp3 Player");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Toolkit kit = getToolkit();
        Dimension size = kit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        loadMenu();
        initComponents();
        pack();
        setResizable(false);
    }

    private void loadMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem openFileItem = new JMenuItem("Open");
        openFileItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        fileMenu.add(openFileItem);

        JMenu playMenu = new JMenu("Play");
        JMenuItem playItem = new JMenuItem("Start (ALT + P)");
        playItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });
        JMenuItem stopItem = new JMenuItem("Stop (ALT + S)");
        stopItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        playMenu.add(playItem);
        playMenu.add(stopItem);
        menuBar.add(fileMenu);
        menuBar.add(playMenu);
        setJMenuBar(menuBar);
    }

    private void initComponents() {
        JPanel panButtons = new JPanel();
        panButtons.setLayout(new FlowLayout());
        JPanel panSlider = new JPanel();
        panSlider.setLayout(new FlowLayout());
        JPanel panRoot = new JPanel();
        panRoot.setLayout(new BorderLayout());
        panRoot.add(panButtons, BorderLayout.NORTH);
        panRoot.add(panSlider, BorderLayout.SOUTH);

        getContentPane().add(panRoot);

        JButton play = new JButton("Play");
        play.setSize(10, 10);
        play.setBounds(20, 50, 50, 20);
        play.setMnemonic(KeyEvent.VK_P);
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        JButton pause = new JButton("Pause");
        pause.setBounds(100, 50, 50, 20);
        pause.setMnemonic(KeyEvent.VK_P);
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        JButton stop = new JButton("Stop");
        stop.setBounds(180, 50, 50, 20);
        stop.setMnemonic(KeyEvent.VK_S);
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        JButton open = new JButton("Open");
        open.setBounds(260, 50, 50, 20);
        open.setMnemonic(KeyEvent.VK_O);
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        panButtons.add(play);
        panButtons.add(pause);
        panButtons.add(stop);
        panButtons.add(open);

        slider = new JSlider(SwingConstants.HORIZONTAL);
        slider.setValue(0);
        slider.addChangeListener(this);
        slider.setMinorTickSpacing(10);
        panSlider.add(slider);
    }

    private ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private boolean openFile() {
        JFileChooser fc = new JFileChooser(Controller.getInstance().getDir());
        fc.setMultiSelectionEnabled(true);
        if (fc.showOpenDialog(GuiPlayer.this) == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            // if (!Controller.getInstance().setFile(file)) {
            // JOptionPane.showMessageDialog(this, "Error while opening file " +
            // file.getName());
            // }

            Controller.getInstance().setPlayList(files);
            Controller.getInstance().setDir(files[0]);
            return true;
        }
        return false;
    }

    private void play() {
        System.out.println("play");

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                slider.setValue(0);
                Controller.getInstance().play();
            }
        });
        if (Controller.getInstance().isFileLoaded() || openFile()) {
            t.start();
        }
    }

    private void stop() {
        System.out.println("stop");
        Controller.getInstance().stop();
    }

    private void pause() {
        System.out.println("pause");
        Controller.getInstance().pause();
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        if (!slider.getValueIsAdjusting()) {
            Controller.getInstance().setFrames(slider.getValue());
        }
    }
}
