package paint_project;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

class DrawingPanel extends JPanel {

    private ArrayList<Shape> shapes = new ArrayList<>();
    private Color currentColor = Color.BLACK;
    private int currentShape = 0;
    private boolean drawing = false;
    private Point startPoint;
    private Point endPoint;
    private boolean filled = false;
    private ArrayList<Point> freehandPoints = new ArrayList<>();

    public DrawingPanel() {
        setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (currentShape == 3) {
                    drawing = true;
                    freehandPoints.clear();
                    freehandPoints.add(startPoint);
                } else {
                    shapes.add(createShape(startPoint, startPoint, currentColor,filled));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                if (currentShape == 3) {
                    drawing = false;
                    shapes.add(new Freehand(freehandPoints, currentColor));
                    freehandPoints.clear();  // Clear the points after adding the Freehand shape
                } else {
                    shapes.add(createShape(startPoint, endPoint, currentColor, filled));
                }
                repaint();
                startPoint = null;
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                if (currentShape == 3) {
                    if (drawing) {
                        freehandPoints.add(endPoint);
                        repaint();
                    }
                } else {
                    shapes.remove(shapes.size() - 1);
                    shapes.add(createShape(startPoint, endPoint, currentColor, filled));
                    repaint();
                }
            }
        });
    }

    private Shape createShape(Point start, Point end, Color color, boolean filled) {
        switch (currentShape) {
            case 0: 
                return new Line(start, end, color);
            case 1: 
                return new Rectangle(start, end, color, filled);
            case 2:
                return new Oval(start, end, color, filled);
            case 4: 
                return new Eraser(start, end, color);
            default:
                return null;
        }
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public void setCurrentShape(int shape) {
        currentShape = shape;
    }

    public int getCurrentShape() {
        return currentShape;
    }

   

    public void setFilled(boolean isFilled) {
        filled = isFilled;
        repaint();
    }

    public void clearAll() {
        shapes.clear();
        repaint();
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (drawing && currentShape == 3) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(currentColor);
            g2d.setStroke(new BasicStroke());
            for (int i = 1; i < freehandPoints.size(); i++) {
                Point p1 = freehandPoints.get(i - 1);
                Point p2 = freehandPoints.get(i);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public void loadImage(BufferedImage img) {
        shapes.add(new ImageShape(img, new Point(0, 0)));
        repaint();
    }

    abstract class Shape {
        abstract void draw(Graphics g);
    }

    class Line extends Shape {

        private final Point start;
        private final Point end;
        private final Color color;

        public Line(Point start, Point end, Color color) {
            this.start = start;
            this.end = end;
            this.color = color;
        
        }

        @Override
        void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);

             g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, null, 0.0f));
           
            g2d.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    class Rectangle extends Shape {

        private final Point start;
        private final Point end;
        private final Color color;
        private final boolean filled;

        public Rectangle(Point start, Point end, Color color,  boolean filled) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.filled = filled;
        }

        @Override
        void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);

            int width = Math.abs(end.x - start.x);
            int height = Math.abs(end.y - start.y);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);

            if (filled) {
                g2d.fillRect(x, y, width, height);
            } else {
                g2d.drawRect(x, y, width, height);
            }
        }
    }

    class Oval extends Shape {

        private final Point start;
        private final Point end;
        private final Color color;
        private final boolean filled;

        public Oval(Point start, Point end, Color color, boolean filled) {
            this.start = start;
            this.end = end;
            this.color = color;
            this.filled = filled;
        }

        @Override
        void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, null, 0.0f));
            

            int width = Math.abs(end.x - start.x);
            int height = Math.abs(end.y - start.y);
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);

            if (filled) {
                g2d.fillOval(x, y, width, height);
            } else {
                g2d.drawOval(x, y, width, height);
            }
        }
    }

    class ImageShape extends Shape {

        private final BufferedImage image;
        private final Point position;

        public ImageShape(BufferedImage image, Point position) {
            this.image = image;
            this.position = position;
        }

        @Override
        void draw(Graphics g) {
            g.drawImage(image, position.x, position.y, null);
        }
    }

    class Freehand extends Shape {

        private final ArrayList<Point> points;
        private final Color color;

        public Freehand(ArrayList<Point> points, Color color) {
            this.points = new ArrayList<>(points);
            this.color = color;
        }

        @Override
        void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);

            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
                Point p2 = points.get(i);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    class Eraser extends Rectangle {
        public Eraser(Point start, Point end, Color color) {
            super(start, end, Color.WHITE, true);
        }

        @Override
        void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.draw(g2d);

        }
    }
}

