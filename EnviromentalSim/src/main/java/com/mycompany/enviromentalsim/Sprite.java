/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.enviromentalsim;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;


/**
 * Class to hold a generic sprite object moving around the screen
 * @author Adam Cassidy
 */
public class Sprite extends JLabel {
    // physical parameters    
    int rank; // range of 1 to 10.  controls where this animal lies on the food chain.
    int health;  // range of 1 to 10.  controls how much health this animal currently has.  slowly depletes over time
    int maxhealth; // range of 1 to 10.  cotnrols the maximum amount of health this animal can hold.
    int strength;  // range of 1 to 5.  controls how much health this animal removes from others on attacks.
    int size;  // radius of this sprite in pixels.
    boolean dead; // determines if this sprite has died.  changes drawing behavior
    int[] position; // used to draw the sprite based on its calculated position within the window. [X, Y]
    
    // behavior parameters
    ArrayList<Integer> diet; // lists the ranks of sprites this sprite might want to eat
    ArrayList<Integer> knownpredators; // lists the ranks of other sprites that have attacked this sprite before, allowing it to learn and run if needs
    int reproduction; // range of 0 to 5. controls how often this sprite will produce a copy of itself (simulated reproduction)
    int speed; // range of 0 to 5.  controls how fast the sprite can move 
    int range; // range of 0 to 5.  controls how far the sprite can move before switching directions
    
    int aggressiveness; // range of 0 to 5.  controls how likely the sprite is to chase after another sprite of weaker strength
    int sight; // range of 0 to 5.  controls how far this sprite can detect other sprites.
    int[] angle;  // represents the angle this sprite is currently moving with on a cartesian plane.  [+/- X, +/-Y]    
    
    // counters
    int range_travel; // running count of how far we are within a range travel
    
    // Constructors
    public Sprite(int rank, int[] position) {
        this.rank = rank;
        this.position = position;
    }
    
    public Sprite(int rank, int health, int maxhealth, int strength, int size, boolean dead, int[] position, ArrayList<Integer> diet, ArrayList<Integer> knownpredators, int reproduction, int speed, int range, int aggressiveness, int sight, int[] angle) {
        this.rank = rank;
        this.maxhealth = maxhealth;
        this.health = health;
        this.strength = strength; 
        this.size = size;
        this.dead = dead;
        this.position = position;
        this.diet = diet;
        this.knownpredators = knownpredators;
        this.reproduction = reproduction;
        this.speed = speed;
        this.range = range;
        this.aggressiveness = aggressiveness;
        this.sight = sight;
        this.angle = angle;
        this.setLocation(this.position[0], this.position[1]);
    }
    
    /**
     * Main function of movement of this sprite.  Factors in all of its 
     */  
    public void move(ArrayList<Sprite> sprites, JPanel roamingarea) {
        // in case this is called while the sprite is dead
        if (dead) {
            this.setVisible(false);
            this.setEnabled(false);
            sprites.remove(this);
            return;
        } 

        // detect if another sprite is touching this sprite. delete that sprite and max out health if on its diet
        for (Sprite sprite : sprites) {
            int distance = distanceFrom(sprite);
            if (distance <= this.size / 2 + sprite.size / 2) {
                // check that the other sprite is on the menu
                if (diet.contains(sprite.rank)) { 
                    // attack our prey
                    this.attack(sprite);
                    
                    if (sprite.dead == true)
                        // heal this sprite half the health of the other sprite 
                        this.heal(sprite.health / 4);
                }                
            }
            // detect if another sprite is within vision range
            else if (distance <= this.sight) {
                // currently, a sprite will always favor running from a predator over satisfying their diet
                // todo: determine this based on aggressiveness level. let sprites fight back?
                    // if other sprite is a predator, run
                    if(this.knownpredators.contains(sprite.rank)) {
                        // set our angle to opposite the direction of the predatory sprite
                        angle[0] = this.position[0] - sprite.position[0];
                        angle[1] = this.position[1] - sprite.position[1];
                        
                        // purposely do not alter range so that we must adjust course after our range
                    }

                    // else if other sprite on diet
                    else if (this.diet.contains(sprite.rank)) {
                        // decide if this sprite will chase after the other sprite based on health and aggression
                        Random rand = new Random();
                        int likelihood = rand.nextInt(5) + 8; // gives random int from 8 to 12
                        // turn this in to a multiplier for the health level
                        double multiplier = ((double) likelihood) / 10;                       
                        if ((this.health / this.maxhealth) * multiplier < 75) {
                            // set our course to follow our prey
                            this.angle[0] = sprite.position[0] - this.position[0];
                            this.angle[1] = sprite.position[1] - this.position[1];
                        }
                    }
            }                        
        }        
              
        this.moveSprite(roamingarea);      
        
        // subtract from our range
        range_travel -= 1;
        
        // reproduce???
        
        this.draw();
        this.revalidate();
        this.repaint();                        
    }
    
    /**
     * Main function of drawing this sprite.     
     */
    public void draw() {
        // draw this sprite's image at location if not dead
        if (dead) {
            this.setVisible(false);
            this.setEnabled(false);
            return;
        }
        
        // update the location of the jlabel to the new location
        this.setLocation(this.position[0], this.position[1]);                
    }
    
    /**
     * Moves the sprite and checks for border collisions
     */
    public void moveSprite(JPanel roamingarea) {
        // run until we will not be out of bounds
        // this is definitely the lazy way, but eventually a random number should always point inbounds
        boolean newangle = false;
        while (true) {
            // if our range has run out, reset angle randomly and start again
            if (this.range_travel <= 0) {
                Random rand = new Random();
                this.angle[0] = rand.nextInt(10);
                if (rand.nextInt(2) > 0) this.angle[0] *= -1;
                this.angle[1] = rand.nextInt(10);
                if (rand.nextInt(2) > 0) this.angle[1] *= -1;     
                newangle = true;
            }

            // calculate the next pixel position based on speed and angle
            // https://www.geeksforgeeks.org/strictmath-hypot-method-in-java/ hypotenuse of a triangle with entered sides
            double hyp = StrictMath.hypot(Math.abs(this.angle[0]), Math.abs(this.angle[1]));
            this.position[0] += (int)(this.angle[0] / hyp * speed);
            this.position[1] += (int)(this.angle[1] / hyp * speed);
            
            //System.out.println(String.format("%d %d %d", (int)hyp, this.angle[0], this.angle[1]));
            
            if (position[0] > this.size && position[0] <= roamingarea.getWidth() - this.size * 2 &&
                position[1] > this.size && position[1] <= roamingarea.getHeight() - this.size * 2) {                
                break;
            }
            range_travel = 0;
        }
        
        if (newangle) this.range_travel = this.range;
    }
    
    /**
     * Returns the absolute value pixel length distance between this sprite and the passed in sprite using coordinates.
     * 
     * It is OKAY here to use an integer since exact location is not important.  
     * @param sprite - another sprite object with a declared location
     * @return euclidean distance between this sprite and the passed in sprite in pixels
     */
    public int distanceFrom(Sprite sprite) {
        // basic distance formula
        return (int) (Math.sqrt(Math.pow(this.position[0] - sprite.position[0], 2) + Math.pow(this.position[1] - sprite.position[1], 2)));
    }
    
    /**
     * Calls the takeDamage on the sprite this sprite is attacking
     * @param sprite - this sprite object
     */
    public void attack(Sprite sprite) {
        sprite.takeDamage(this);
    }
        
    /**
     * Called by other sprites that pass themselves into the function
     * @param sprite - the sprite that is attacking this sprite
     */
    public void takeDamage(Sprite sprite) {
        // calculate damage to take based on attacking sprite's strength
        this.health -= sprite.strength;
        if (this.health <= 0) this.dead = true;
        // add the attacking sprite to our known predators list
        if (!this.knownpredators.contains(sprite.rank)) this.knownpredators.add(sprite.rank);
    }
    
    /**
     * Attempts to heal this sprite with given hp value.  Ensures we don't set a health above the max.
     * @param hp - number of health points to add to this sprite
     */
    public void heal(int hp) {
        this.health += hp;
        if (health > maxhealth) health = maxhealth;
    }
    
}
