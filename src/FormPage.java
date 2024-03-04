import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import org.ini4j.Ini;

import com.formdev.flatlaf.FlatDarculaLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The {@code FormPage} class creates a GUI application for updating Fortnite settings.
 * It allows users to adjust settings such as:
 *      Screen Resolution,
 *      NVIDIA Reflex Low Latency,
 *      mouse acceleration,
 *      Fullscreen Mode.
 * The settings are saved in an INI file.
 */
public class FormPage extends JFrame {

    static final String INI_FILE_NAME = "testFile.ini";
    static final String INI_SECTION_NAME = "/Script/FortniteGame.FortGameUserSettings";
    static final String INI_XRES_KEY_NAME = "ResolutionSizeX";
    static final String INI_YRES_KEY_NAME = "ResolutionSizeY";

    /**
     * Constructs a {@code FormPage} object.
     * This constructor initializes the UI components and sets up event listeners.
     *
     * @throws IOException To manage exceptions when utilising the ini4j package.
     */
    public FormPage() throws IOException {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch(Exception ex) {
            System.err.println("Failed to init LaF");
        }
        
        JFrame frame = new JFrame("Fortnite Settings Updater - @DLNOnGithub");
        Image logo = ImageIO.read(new File("resources/ApplicationLogo.png"));
        frame.setIconImage(logo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(515, 430);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#292928"));

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setBackground(Color.decode("#414140"));
        topLeftPanel.setBounds(10, 10, 235, 150);
        TitledBorder tlpTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Resolution:");
        Font tlpFont = tlpTitledBorder.getTitleFont();
        tlpTitledBorder.setTitleColor(Color.WHITE);
        tlpTitledBorder.setTitleFont(tlpFont.deriveFont(Font.BOLD));
        topLeftPanel.setBorder(tlpTitledBorder);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setBackground(Color.decode("#414140"));
        topRightPanel.setBounds(255, 10, 235, 150);
        TitledBorder trpTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "NVIDIA Reflex Low Latency:");
        trpTitledBorder.setTitleColor(Color.WHITE);
        Font trpFont = trpTitledBorder.getTitleFont();
        trpTitledBorder.setTitleFont(trpFont.deriveFont(Font.BOLD));
        topRightPanel.setBorder(trpTitledBorder);
        topRightPanel.setLayout(new GridLayout(3, 1));

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.decode("#414140"));
        bottomLeftPanel.setBounds(10, 170, 235, 150);
        TitledBorder blpTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Mouse Acceleration:");
        blpTitledBorder.setTitleColor(Color.WHITE);
        Font blpFont = blpTitledBorder.getTitleFont();
        blpTitledBorder.setTitleFont(blpFont.deriveFont(Font.BOLD));
        bottomLeftPanel.setBorder(blpTitledBorder);

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.decode("#414140"));
        bottomRightPanel.setBounds(255, 170, 235, 150);
        TitledBorder brpTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Fullscreen Mode:");
        brpTitledBorder.setTitleColor(Color.WHITE);
        Font brpFont = brpTitledBorder.getTitleFont();
        brpTitledBorder.setTitleFont(brpFont.deriveFont(Font.BOLD));
        bottomRightPanel.setBorder(brpTitledBorder);
        bottomRightPanel.setLayout(new GridLayout(3, 1));

        String currentXResolution = getResolutionSettings("x");
        String currentYResolution = getResolutionSettings("y");

        JLabel xLabel = new JLabel(" x ");
        xLabel.setForeground(Color.WHITE);
        Font xlFont = xLabel.getFont();
        xLabel.setFont(xlFont.deriveFont(Font.BOLD));

        JTextArea maSettingsLabel = new JTextArea("On = Mouse Acceleration is Enabled\nOff = Mouse Acceleration is Disabled");
        maSettingsLabel.setForeground(Color.WHITE);
        maSettingsLabel.setBackground(Color.decode("#414140"));

        JRadioButton nrllOff = new JRadioButton("Off");
        nrllOff.setForeground(Color.WHITE);
        JRadioButton nrllOn = new JRadioButton("On");
        nrllOn.setForeground(Color.WHITE);
        JRadioButton nrllOnBoost = new JRadioButton("On + Boost");
        nrllOnBoost.setForeground(Color.WHITE);
        ButtonGroup nrllButtonGroup = new ButtonGroup();
        nrllButtonGroup.add(nrllOff);
        nrllButtonGroup.add(nrllOn);
        nrllButtonGroup.add(nrllOnBoost);

        String llmSettingValue = getNvidiaLowLatencySetting();
        switch (llmSettingValue) {
            case "0":
                nrllOff.setSelected(true);
                break;
            case "1":
                nrllOn.setSelected(true);
                break;
            case "2":
                nrllOnBoost.setSelected(true);
                break;
            case null:
                break;
            default:
                System.out.println("This value is invalid!");
        }

        JRadioButton mouseAccelerationOn = new JRadioButton("On");
        mouseAccelerationOn.setForeground(Color.WHITE);
        JRadioButton mouseAccelerationOff = new JRadioButton("Off");
        mouseAccelerationOff.setForeground(Color.WHITE);
        ButtonGroup MouseAccelerationButtonGroup = new ButtonGroup();
        MouseAccelerationButtonGroup.add(mouseAccelerationOn);
        MouseAccelerationButtonGroup.add(mouseAccelerationOff);

        String mouseAccelerationSetting = getMouseAccelerationSetting();
        switch (mouseAccelerationSetting) {
            case "True":
                mouseAccelerationOff.setSelected(true);
                break;
            case "False":
                mouseAccelerationOn.setSelected(true);
                break;
            case null:
                System.out.println("Error with file value!");
                break;
            default:
                System.out.println("This value is invalid!");
        }

        JRadioButton screenModeFullscreen = new JRadioButton("Fullscreen");
        screenModeFullscreen.setForeground(Color.WHITE);
        JRadioButton screenModeWindowedFullscreen = new JRadioButton("Windowed Fullscreen");
        screenModeWindowedFullscreen.setForeground(Color.WHITE);
        JRadioButton screenModeWindowed = new JRadioButton("Windowed");
        screenModeWindowed.setForeground(Color.WHITE);
        ButtonGroup screenModeButtonGroup = new ButtonGroup();
        screenModeButtonGroup.add(screenModeFullscreen);
        screenModeButtonGroup.add(screenModeWindowedFullscreen);
        screenModeButtonGroup.add(screenModeWindowed);

        String screenModeSetting = getScreenMode();
        switch (screenModeSetting) {
            case "0":
                screenModeFullscreen.setSelected(true);
                break;
            case "1":
                screenModeWindowedFullscreen.setSelected(true);
                break;
            case "2":
                screenModeWindowed.setSelected(true);
                break;
            case null:
                System.out.println("Error with file value!");
                break;
            default:
                System.out.println("This value is invalid!");
        }

        JButton applyButton = new JButton("Apply");
        applyButton.setBackground(Color.decode("#414140"));
        applyButton.setBounds(10, 330, 480, 50);
        applyButton.setForeground(Color.WHITE);
        applyButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.WHITE)));


        JTextField xResolutionWanted = new JTextField(currentXResolution);
        JTextField yResolutionWanted = new JTextField(currentYResolution);

        ((AbstractDocument) xResolutionWanted.getDocument()).setDocumentFilter(new IntFilter());
        xResolutionWanted.setForeground(Color.decode("#f1f0f1"));

        ((AbstractDocument) yResolutionWanted.getDocument()).setDocumentFilter(new IntFilter());
        yResolutionWanted.setForeground(Color.decode("#f1f0f1"));

        frame.add(topLeftPanel);
        frame.add(topRightPanel);
        frame.add(bottomLeftPanel);
        frame.add(bottomRightPanel);
        frame.add(applyButton);

        topLeftPanel.add(xResolutionWanted);
        topLeftPanel.add(xLabel);
        topLeftPanel.add(yResolutionWanted);
        topRightPanel.add(nrllOff);
        topRightPanel.add(nrllOn);
        topRightPanel.add(nrllOnBoost);
        bottomLeftPanel.add(maSettingsLabel);
        bottomLeftPanel.add(mouseAccelerationOn);
        bottomLeftPanel.add(mouseAccelerationOff);
        bottomRightPanel.add(screenModeFullscreen);
        bottomRightPanel.add(screenModeWindowedFullscreen);
        bottomRightPanel.add(screenModeWindowed);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File iniFile = new File(INI_FILE_NAME);

                try {
                    int xResolution = Integer.parseInt(xResolutionWanted.getText());
                    int yResolution = Integer.parseInt(yResolutionWanted.getText());

                    if (xResolution < 800 || xResolution > 3840) {
                        JOptionPane.showMessageDialog(frame, "X Resolution must be between 800 and 3840.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (yResolution < 450 || yResolution > 2160) {
                        JOptionPane.showMessageDialog(frame, "Y Resolution must be between 450 and 2160.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "X Resolution must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!iniFile.exists()) {
                    JOptionPane.showMessageDialog(frame, "The settings file for Fortnite does cannot be found on this computer.", "Settings file not found", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    Ini ini = new Ini(iniFile);

                    iniFile.setWritable(true);

                    ini.put(INI_SECTION_NAME, "ResolutionSizeX", xResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "LastUserConfirmedResolutionSizeX", xResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "DesiredScreenWidth", xResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "LastUserConfirmedDesiredScreenWidth", xResolutionWanted.getText());

                    ini.put(INI_SECTION_NAME, "ResolutionSizeY", yResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "LastUserConfirmedResolutionSizeY", yResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "DesiredScreenHeight", yResolutionWanted.getText());
                    ini.put(INI_SECTION_NAME, "LastUserConfirmedDesiredScreenHeight", yResolutionWanted.getText());

                    if (nrllOff.isSelected()) {
                        ini.put(INI_SECTION_NAME, "LatencyTweak2", "0");
                    } else if (nrllOn.isSelected()) {
                        ini.put(INI_SECTION_NAME, "LatencyTweak2", "1");
                    } else if (nrllOnBoost.isSelected()) {
                        ini.put(INI_SECTION_NAME, "LatencyTweak2", "2");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No NVIDIA Reflex Low Latency option is selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (mouseAccelerationOff.isSelected()) {
                        ini.put(INI_SECTION_NAME, "bDisableMouseAcceleration", "True");
                    } else if (mouseAccelerationOn.isSelected()) {
                        ini.put(INI_SECTION_NAME, "bDisableMouseAcceleration", "False");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No Mouse Acceleration option is selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (screenModeFullscreen.isSelected()) {
                        ini.put(INI_SECTION_NAME, "FullscreenMode", "0");
                        ini.put(INI_SECTION_NAME, "LastConfirmedFullscreenMode", "0");
                        ini.put(INI_SECTION_NAME, "PreferredFullscreenMode", "0");
                    } else if (screenModeWindowedFullscreen.isSelected()) {
                        ini.put(INI_SECTION_NAME, "FullscreenMode", "1");
                        ini.put(INI_SECTION_NAME, "LastConfirmedFullscreenMode", "1");
                        ini.put(INI_SECTION_NAME, "PreferredFullscreenMode", "1");
                    } else if (screenModeWindowed.isSelected()) {
                        ini.put(INI_SECTION_NAME, "FullscreenMode", "2");
                        ini.put(INI_SECTION_NAME, "LastConfirmedFullscreenMode", "2");
                        ini.put(INI_SECTION_NAME, "PreferredFullscreenMode", "2");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No Fullscreen Mode option is selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }

                    ini.store();

                    JOptionPane.showMessageDialog(frame, "Your file has been saved successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);

                    iniFile.setReadOnly();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "The file could not be saved. Make sure you are running as an administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    /**
     * Retrieves the current resolution setting for the game from the INI file.
     *
     * @param axis The axis we are trying to retrieve.
     * @return The resolution setting value that is currently being used.
     * @throws IOException If an error occurs while reading the INI file.
     */
    private String getResolutionSettings(String axis) throws IOException {
        File iniFile = new File(INI_FILE_NAME);
        if (iniFile.exists()) {

            Ini ini = new Ini(iniFile);

            if (axis.equals("x")) {
                return ini.get(INI_SECTION_NAME, INI_XRES_KEY_NAME);
            }
            if (axis.equals("y")) {
                return ini.get(INI_SECTION_NAME, INI_YRES_KEY_NAME);
            }
        }
    return null;
    }

    /**
     * Retrieves the NVIDIA Low Latency setting from the INI file.
     *
     * @return The NVIDIA Low Latency setting, or {@code null} if not found or an error occurs.
     * @throws IOException IOException If an error occurs while reading the INI file.
     */
    private String getNvidiaLowLatencySetting() throws IOException {
        File iniFile = new File(INI_FILE_NAME);

        if (iniFile.exists()) {

            Ini ini = new Ini(iniFile);
            return ini.get(INI_SECTION_NAME, "LatencyTweak2");
        }
        return null;
    }

    /**
     * Retrieves the mouse acceleration setting from the INI file.
     *
     * @return The mouse acceleration setting, or {@code null} if not found / an error occurs.
     * @throws IOException If an error occurs while reading the INI file.
     */
    private String getMouseAccelerationSetting() throws IOException {
        File iniFile = new File(INI_FILE_NAME);

        if (iniFile.exists()) {

            Ini ini = new Ini(iniFile);
            return ini.get(INI_SECTION_NAME, "bDisableMouseAcceleration");
        }
        return null;
    }

    /**
     * Retrieves the screen mode (fullscreen/windowed) setting from the INI file.
     *
     * @return The screen mode setting, or {@code null} if not found or an error occurs.
     * @throws IOException If an error occurs while reading the INI file.
     */
    private String getScreenMode() throws IOException {
        File iniFile = new File(INI_FILE_NAME);

        if (iniFile.exists()) {

            Ini ini = new Ini(iniFile);
            return ini.get(INI_SECTION_NAME, "FullscreenMode");
        }
        return null;
    }
}
