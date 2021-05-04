package edu.episen.si.ing1.pds.client.swing.global.shared;

import java.awt.*;
import java.util.HashMap;

public class Ui {
    public static final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_COPYRIGHT = new Font("ROBOTO LIGHT", Font.PLAIN, 12);
    public static final Color COLOR_OUTLINE = new Color(103, 107, 120);
    public static final Color COLOR_BACKGROUND = new Color(37, 47, 61);
    public static final Color COLOR_INTERACTIVE = new Color(54, 38, 90);
    public static final Color COLOR_INTERACTIVE_DARKER = new Color(72, 64, 92);
    public static final Color OFFWHITE = new Color(229, 229, 229);
    public static final String BUTTON_TEXT_LOGIN = "Accéder au système";
    public static final int ROUNDNESS = 8;

    public static Graphics2D get2dGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {{
            put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }});
        return g2;
    }
}
