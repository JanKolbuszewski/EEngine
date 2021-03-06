package demo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import core.PhysicsObject;
import soundeffects.CollisionSound;

public class Physics extends Thread
{
    private static DecimalFormat df = new DecimalFormat("0.###");
    int ScreenSizeX, ScreenSizeY;
    ArrayList<PhysicsObject> objects;
    ArrayList<Force> Forces;
    CollisionSound sound;
    File collisionSound = new File("Jump.wav");
    
    boolean log = false;

    public Physics(int ScreenSizeX, int ScreenSizeY)
    {
        this.ScreenSizeX = ScreenSizeX;
        this.ScreenSizeY = ScreenSizeY;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Update(objects);
                Thread.sleep(5);
            }
            catch (InterruptedException ex) { }
        }
    }

    public void setObjects(ArrayList<PhysicsObject> Balls)
    {
        this.objects = Balls;
    }

    public void setForces(ArrayList<Force> Forces)
    {
        this.Forces = Forces;
    }

    public void Update(ArrayList<PhysicsObject> Objects)
    {
        for(int i=0; i<Objects.size(); i++)
        {
        //    CheckWalls(Objects.get(i));
            for(int j=0; j<Objects.size(); j++)
                if(j>i) CheckCollisions(Objects.get(i), Objects.get(j));

            for(Force f : Forces)
                f.ApplyForce(Objects.get(i));

            move(Objects.get(i));
        }
    }

    public void move(PhysicsObject obj)
    {
        if(obj.movable)
        {
            if (obj.getSpeed() > Math.pow(obj.maxSpeed, 2)) // x^2 + y^2 > maxSpeed^2
            {
                obj.setxSpeed(obj.maxSpeed * Math.cos(obj.getSpeedAngle()));
                obj.setySpeed(obj.maxSpeed * Math.sin(obj.getSpeedAngle()));
            }

            obj.setX(obj.getX() + obj.getxSpeed());
            obj.setY(obj.getY() + obj.getySpeed());
        }
        obj.angle += obj.rotationSpeed;
    }

/*
    public void CheckWalls(PhysicsObject obj)
    {
        if(obj.type.equals("Circle"))
        {
            if (obj.getX()-obj.getDiameter()/2 < 0)
            {
                obj.setX(obj.getDiameter()/2);
                obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
            }
            else if (obj.getX()+obj.getDiameter()/2 > ScreenSizeX-20)
            {
                obj.setX(ScreenSizeX-20-obj.getDiameter()/2);
                obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
            }

            if (obj.getY() - obj.getDiameter()/2 < 0)
            {
                obj.setY(obj.getDiameter()/2);
                obj.setySpeed(-obj.getySpeed()*obj.elasticity);
            }
            else if (obj.getY()+obj.getDiameter()/2 > ScreenSizeY-40)
            {
                obj.setY(ScreenSizeY-40-obj.getDiameter()/2);
                obj.setySpeed(-obj.getySpeed()*obj.elasticity);
            }
        }

        else if(obj.type.equals("Rectangle"))
        {
            Rectangle temp = (Rectangle) obj;

            ArrayList<Point2D> points = temp.getPoints();
            for(int i=0; i<1; i++)
            {
                if (points[i].getX()<0)
                {
                //    System.out.println(i);
                    obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
                }
                else if (points[i].getX()>ScreenSizeX-20)
                {
                 //   System.out.println(i);
                    obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
                }

                if (points[i].getY()<0)
                {
                //    System.out.println(i);
                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
                }
                else if (points[i].getY()>ScreenSizeY-40)
                {
                //    System.out.println(i);
                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
                }

            }
        }
    }
*/
    double AngleBetween(Point2D P1, Point2D P2)
    {
        return Math.atan2(P2.getY() - P1.getY(), P2.getX() - P1.getX());
    }

    public void CheckCollisions(PhysicsObject objOne, PhysicsObject objTwo)
    {
        if(objOne.movable || objTwo.movable)    //  check only if it's necessary
        {
        	
        	//sound.playSound(collisionSound);
        	
            if(objOne.type.equals("Circle") && objTwo.type.equals("Circle"))
            {
                double distance = Math.sqrt( Math.pow(objOne.getX()-objTwo.getX(),2) + Math.pow(objOne.getY()-objTwo.getY(),2) );

                if(distance <= (objOne.getDiameter()/2 + objTwo.getDiameter()/2))
                {
                    if(objOne.movable && objTwo.movable)    //  both are movable
                    {
                        double commonMass = objOne.getMass()+objTwo.getMass();
                        double commonElasticy = objOne.getElasticity()*objTwo.getElasticity();

                        // prevents objects from entering each other
                        Vector D1 = new Vector(((objOne.getDiameter()/2 + objTwo.getDiameter()/2) - distance) * objTwo.mass / commonMass, AngleBetween(objTwo.getXY(), objOne.getXY()), true);
                        Vector D2 = new Vector(((objOne.getDiameter()/2 + objTwo.getDiameter()/2) - distance) * objOne.mass / commonMass, AngleBetween(objOne.getXY(), objTwo.getXY()), true);

                        objOne.setX(objOne.getX() + D1.getX());
                        objOne.setY(objOne.getY() + D1.getY());
                        objTwo.setX(objTwo.getX() + D2.getX());
                        objTwo.setY(objTwo.getY() + D2.getY());

                        //momentums
                        Vector V1 = objOne.getSpeedVector();
                        Vector V2 = objTwo.getSpeedVector();

                        //elastic
                        Vector CosV1 = new Vector(objOne.getSpeed()*Math.cos(objOne.getSpeedAngle()-AngleBetween(objTwo.getXY(), objOne.getXY())), AngleBetween(objTwo.getXY(), objOne.getXY()), true);
                        Vector SinV1 = V1.sub(CosV1);
                        Vector CosV2 = new Vector(objTwo.getSpeed()*Math.cos(objTwo.getSpeedAngle()-AngleBetween(objOne.getXY(), objTwo.getXY())), AngleBetween(objOne.getXY(), objTwo.getXY()), true);
                        Vector SinV2 = V2.sub(CosV2);

                        if(log)
                        {
                            System.out.println("> " + objOne.getSpeedVector().toString() + " energy: " + df.format(Math.pow(objOne.getSpeed(), 2) * objOne.getMass()) + " angle = " + df.format(Math.toDegrees(objOne.getSpeedAngle() - AngleBetween(objTwo.getXY(), objOne.getXY()))));
                            System.out.println("CosV1 = " + df.format(Math.cos(objOne.getSpeedAngle() - AngleBetween(objTwo.getXY(), objOne.getXY()))) + "      " + CosV1.toString());
                            System.out.println("SinV1 = " + df.format(Math.sin(objOne.getSpeedAngle() - AngleBetween(objTwo.getXY(), objOne.getXY()))) + "      " + SinV1.toString());
                            System.out.println("");
                            System.out.println("> " + objTwo.getSpeedVector().toString() + " energy: " + df.format(Math.pow(objTwo.getSpeed(), 2) * objTwo.getMass()) + " angle = " + df.format(Math.toDegrees(objTwo.getSpeedAngle() - AngleBetween(objOne.getXY(), objTwo.getXY()))));
                            System.out.println("CosV2 = " + df.format(Math.cos(objTwo.getSpeedAngle() - AngleBetween(objOne.getXY(), objTwo.getXY()))) + "      " + CosV2.toString());
                            System.out.println("SinV2 = " + df.format(Math.sin(objTwo.getSpeedAngle() - AngleBetween(objOne.getXY(), objTwo.getXY()))) + "      " + SinV2.toString());
                            System.out.println("");
                        }

                        //  non elastic collision speed - common for both objects
                        Vector VnE = V1.mul(objOne.getMass()).add( (V2).mul(objTwo.getMass()) ).div(commonMass);

                        //  elastic parts
                        Vector V1E = V1.add( CosV2.sub(CosV1).mul(objTwo.getMass() ).div(commonMass/2) );
                        Vector V2E = V2.add( CosV1.sub(CosV2).mul(objOne.getMass() ).div(commonMass/2) );

                        objOne.setSpeed(VnE.mul(1-commonElasticy).add( V1E.mul(commonElasticy) ));
                        objTwo.setSpeed(VnE.mul(1-commonElasticy).add( V2E.mul(commonElasticy) ));

                        if(log)
                        {
                            System.out.println("> " + objOne.getSpeedVector().mul(objOne.getMass()).toString() + " energy: " + df.format(Math.pow(objOne.getSpeed(), 2) * objOne.getMass()));
                            System.out.println("> " + objTwo.getSpeedVector().mul(objOne.getMass()).toString() + " energy: " + df.format(Math.pow(objTwo.getSpeed(), 2) * objTwo.getMass()));
                            System.out.println("");
                        }
                    }
                    else    // one is movable
                    {
                        if (!objOne.movable)     // objOne movable, objTwo nonmovable
                        {
                            PhysicsObject temp = objOne;
                            objOne = objTwo;
                            objTwo = temp;
                        }
                        // prevents objects from entering each other
                        Vector D1 = new Vector(((objOne.getDiameter()/2 + objTwo.getDiameter()/2) - distance), AngleBetween(objTwo.getXY(), objOne.getXY()), true);

                        objOne.setX(objOne.getX() + D1.getX());
                        objOne.setY(objOne.getY() + D1.getY());

                        double commonElasticy = objOne.getElasticity() * objTwo.getElasticity();

                        double collisionAngle = AngleBetween(objTwo.getXY(), objOne.getXY())+Math.PI/2;
                        double temp = objOne.getSpeedAngle()-collisionAngle;
                        double bounceAngle = collisionAngle - temp;

                        if(log)
                        {
                            System.out.println(df.format(Math.toDegrees(collisionAngle)));
                            System.out.println(df.format(Math.toDegrees(bounceAngle)));
                        }

                        objOne.setSpeed(new Vector(objOne.getSpeed(), bounceAngle, true).mul(commonElasticy));
                    }
                }
            }

            if(objOne.type.equals("Rectangle") && objTwo.type.equals("Circle") || objOne.type.equals("Circle") && objTwo.type.equals("Rectangle"))
            {
                if (objOne.type.equals("Rectangle") && objTwo.type.equals("Circle"))
                {
                    PhysicsObject temp = objOne;
                    objOne = objTwo;    //  circle
                    objTwo = temp;      //  rectangle
                }

                Rectangle objTwoR = (Rectangle) objTwo;
                AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(objTwo.getAngle()), objOne.getX(), objOne.getY());

                // sides
                for(int i=0; i<4; i++)
                    objOne.getPoints().get(i).setLocation(objOne.getX() + objOne.getDiameter()/2*Math.cos(i*Math.PI/2), objOne.getY() + objOne.getDiameter()/2*Math.sin(i*Math.PI/2));
                for(int i=0; i<4; i++)
                    rotation.transform(objOne.getPoints().get(i), objOne.getPoints().get(i));

                // corners
                for(int i=4; i<8; i++)
                    objOne.getPoints().get(i).setLocation(objOne.getX() + objOne.getDiameter()/2*Math.cos(AngleBetween(objOne.getXY(), objTwo.getPoints().get(i-4))), objOne.getY() + objOne.getDiameter()/2*Math.sin(AngleBetween(objOne.getXY(), objTwo.getPoints().get(i-4))));

                Line2D[] ballLines = new Line2D[4];

                for (int i=0; i<4; i++)
                    ballLines[i] = new Line2D.Double(objOne.getX(), objTwo.getY(), objOne.getPoints().get(i).getX(), objOne.getPoints().get(i).getY());

                Shape rectangle = objTwoR.getShape();

                Point2D colPoint = null;

                for (Point2D p : objOne.getPoints())
                    if(rectangle.contains(p))   colPoint = p;

                if(colPoint!=null)
                {
                    if(objOne.movable && !objTwo.movable)   // ball is movable, rectangle is not
                    {
                        //  prevents objects from entering each other
                        double cAngle = AngleBetween(objOne.getXY(), colPoint);
                        Point2D colPoint2 = new Point2D.Double(objOne.getX()+objOne.getDiameter()/2*Math.cos(cAngle), objOne.getY()+objOne.getDiameter()/2*Math.sin(cAngle));

                        for(Point2D p : objOne.getPoints())
                            p.setLocation(colPoint2);


                        int i=0;
                        while(rectangle.contains(colPoint2))
                        {
                            objOne.setX(objOne.getX() - Math.cos(cAngle));
                            objOne.setY(objOne.getY() - Math.sin(cAngle));
                            colPoint2.setLocation(objOne.getX()+objOne.getDiameter()/2*Math.cos(cAngle), objOne.getY()+objOne.getDiameter()/2*Math.sin(cAngle));
                        }
                        System.out.println("");

                        double commonElasticy = objOne.getElasticity() * objTwo.getElasticity();

                        double collisionAngle = AngleBetween(colPoint, objOne.getXY())+Math.PI/2;
                        double temp = objOne.getSpeedAngle()-collisionAngle;
                        double bounceAngle = collisionAngle - temp;

                        if(log)
                        {
                            System.out.println("=============================================");
                            System.out.println(df.format(Math.toDegrees(objOne.getSpeedAngle())));
                            System.out.println(df.format(Math.toDegrees(collisionAngle)));
                            System.out.println(df.format(Math.toDegrees(temp)));
                            System.out.println(df.format(Math.toDegrees(bounceAngle)));
                        }

                        objOne.setSpeed(new Vector(objOne.getSpeed(), bounceAngle, true).mul(commonElasticy));

                        if(log) System.out.println("> " + df.format(Math.toDegrees(objOne.getSpeedAngle())));
                    }

                    else if(!objOne.movable && objTwo.movable)   // ball is not movable, rectangle is movable
                    {

                    }
                    else    // both are movable
                    {

                    }
                }
            }

            if(objOne.type.equals("Rectangle") && objTwo.type.equals("Rectangle"))
            {
                // detecting collisions

                if(objOne.movable && objTwo.movable)    //  both are movable
                {
                    //  to do
                }
                else    // one is movable
                {
                    if (!objOne.movable)     // objOne movable, objTwo nonmovable
                    {
                        PhysicsObject temp = objOne;
                        objOne = objTwo;
                        objTwo = temp;
                    }

                    //  to do
                }
            }
        }
    }
}
