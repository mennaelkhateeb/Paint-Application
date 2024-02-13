package paint_project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.net.URL;

public class DrawingProgram extends JFrame {
 
    public static void main(String[] args) {
        DrawingProgram drawingProgram = new DrawingProgram();
    }

    private final JCheckBox filledCheckbox;
    private final DrawingPanel drawingPanel;
    private boolean filled = false;

    public DrawingProgram() {
     
        setTitle("Paint Application");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER );

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout()); 
        add(controlPanel, BorderLayout.PAGE_START );

        JButton redButton = new JButton();
        redButton.setToolTipText("Red");
        redButton.setPreferredSize(new Dimension(30, 20));
        JButton greenButton = new JButton();
        greenButton.setToolTipText("Green");
        greenButton.setPreferredSize(new Dimension(30, 20));
        JButton blueButton = new JButton();
        blueButton.setToolTipText("Blue");
        blueButton.setPreferredSize(new Dimension(30, 20));
        JButton yellowButton = new JButton();
        yellowButton.setToolTipText("Yellow");
        yellowButton.setPreferredSize(new Dimension(30, 20));
        JButton pinkButton = new JButton();
        pinkButton.setToolTipText("Pink");
        pinkButton.setPreferredSize(new Dimension(30, 20));
        JButton orangeButton = new JButton();
        orangeButton.setToolTipText("Orange");
        
        JButton lineButton = createIconButton("line paint.png", new LineButtonListener());
        lineButton.setToolTipText("Line");
        JButton rectangleButton = createIconButton("rectangle.png", new RectangleButtonListener());
        rectangleButton.setToolTipText("Rectangle");
        JButton ovalButton = createIconButton("Ovel.png", new OvalButtonListener());
        ovalButton.setToolTipText("Oval");
        JButton freehandButton = createIconButton("free.png", new FreehandButtonListener());
        freehandButton.setToolTipText("FreeHand");       
        orangeButton.setPreferredSize(new Dimension(30, 20));
        JButton eraserButton = createIconButton("eras.jpg", new EraserButtonListener());
        eraserButton.setToolTipText("Eraser");
        JButton undoButton = createIconButton("undooo.png", new UndoButtonListener());
        undoButton.setToolTipText("Undo");
        JButton clearAllButton =createIconButton("clear paint.png", new ClearAllButtonListener());
        filledCheckbox = new JCheckBox("Filled");
        JButton saveButton = createIconButton("save-icon.png", new SaveButtonListener());
        saveButton.setToolTipText("Save");
        JButton openButton = new JButton("Open");

         redButton.addActionListener(new RedButtonListener());
        greenButton.addActionListener(new GreenButtonListener());
        blueButton.addActionListener(new BlueButtonListener());
        yellowButton.addActionListener(new yellowButtonListener());
        pinkButton.addActionListener(new pinkButtonListener());
        orangeButton.addActionListener(new orangeButtonListener());
        lineButton.addActionListener(new LineButtonListener());
        rectangleButton.addActionListener(new RectangleButtonListener());
        ovalButton.addActionListener(new OvalButtonListener());
        freehandButton.addActionListener(new FreehandButtonListener());
        clearAllButton.addActionListener(new ClearAllButtonListener());
        eraserButton.addActionListener(new EraserButtonListener());
        undoButton.addActionListener(new UndoButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        openButton.addActionListener(new OpenButtonListener());
        filledCheckbox.addItemListener(new FilledCheckboxListener());
        redButton.setBackground(Color.RED);
        greenButton.setBackground(Color.GREEN);
        blueButton.setBackground(Color.BLUE);
        yellowButton.setBackground(Color.yellow);
        pinkButton.setBackground(Color.PINK);
        orangeButton.setBackground(Color.ORANGE);
        lineButton.setBackground(Color.WHITE);
        rectangleButton.setBackground(Color.WHITE);
        ovalButton.setBackground(Color.WHITE);
        freehandButton.setBackground(Color.WHITE);
        eraserButton.setBackground(Color.WHITE);
        clearAllButton.setBackground(Color.WHITE);
        undoButton.setBackground(Color.WHITE);
        saveButton.setBackground(Color.WHITE);
        openButton.setBackground(Color.WHITE);

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        redButton.setFont(buttonFont);
        greenButton.setFont(buttonFont);
        blueButton.setFont(buttonFont);
        yellowButton.setFont(buttonFont);
        pinkButton.setFont(buttonFont);
        orangeButton.setFont(buttonFont);
        lineButton.setFont(buttonFont);
        rectangleButton.setFont(buttonFont);
        ovalButton.setFont(buttonFont);
        freehandButton.setFont(buttonFont);
        eraserButton.setFont(buttonFont);
        clearAllButton.setFont(buttonFont);
        undoButton.setFont(buttonFont);
        saveButton.setFont(buttonFont);
        openButton.setFont(buttonFont);
        Font checkboxFont = new Font("Arial", Font.BOLD, 16);
        filledCheckbox.setFont(checkboxFont);

        controlPanel.add(redButton);
        controlPanel.add(greenButton);
        controlPanel.add(blueButton);
        controlPanel.add(yellowButton);
        controlPanel.add(pinkButton);
        controlPanel.add(orangeButton);
        controlPanel.add(lineButton);
        controlPanel.add(rectangleButton);
        controlPanel.add(ovalButton);
        controlPanel.add(freehandButton);
        controlPanel.add(filledCheckbox);
        controlPanel.add(eraserButton);
        controlPanel.add(undoButton);
        controlPanel.add(clearAllButton);
        controlPanel.add(saveButton);
        controlPanel.add(openButton);
        setVisible(true);
    }

    private JButton createIconButton(String iconFileName, ActionListener listener) {
        URL imageURL = getClass().getResource(iconFileName);

        ImageIcon icon = new ImageIcon(imageURL);
        JButton button = new JButton(icon);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(65, 35));
        Image scaledImage = icon.getImage().getScaledInstance(45, 30, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    private class LineButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentShape(0);
            drawingPanel.setCurrentColor(Color.BLACK);
        }
    }

    private class RectangleButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentShape(1);
            drawingPanel.setCurrentColor(Color.BLACK);
        }
    }

    private class OvalButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentShape(2);
            drawingPanel.setCurrentColor(Color.BLACK);
        }
    }

    private class FreehandButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentShape(3);
            drawingPanel.setCurrentColor(Color.BLACK);
        }
    }

    private class RedButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.RED);
        }
    }

    private class GreenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.GREEN);
        }
    }

    private class BlueButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.BLUE);
        }
    }
    
     private class yellowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.YELLOW);
        }
    }
      private class pinkButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.PINK);
        }
    }
      
       private class orangeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentColor(Color.ORANGE);
        }
    }

    private class EraserButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.setCurrentShape(4); 
            drawingPanel.setCurrentColor(Color.WHITE); 

        }
    }

    private class ClearAllButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.clearAll();
        }
    }

    private class UndoButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (drawingPanel != null) {
                drawingPanel.undo();
            }
        }
    }

    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(DrawingProgram.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = image.createGraphics();
                    drawingPanel.paint(g2d);
                    g2d.dispose();

                    ImageIO.write(image, "png", fileToSave);
                    JOptionPane.showMessageDialog(DrawingProgram.this, "Drawing saved successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DrawingProgram.this, "Error saving drawing.");
                }
            }
        }
    }


    private class FilledCheckboxListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            filled = (e.getStateChange() == ItemEvent.SELECTED);
            drawingPanel.setFilled(filled);
        }
    }

    private class OpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(DrawingProgram.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();

                try {
                    BufferedImage img = ImageIO.read(fileToOpen);
                    drawingPanel.loadImage(img);
                    JOptionPane.showMessageDialog(DrawingProgram.this, "Image loaded successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DrawingProgram.this, "Error loading drawing.");
                }
            }
        }
    }

   
}


