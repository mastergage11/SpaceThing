import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//if you make an array of 10 to allow only 10 bullets on the screen at a time, what happens if that array is filled and you shoot another bullet?
public class Board extends JPanel implements ActionListener {

    Player player;
    Enemy enemy;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Enemy[][] enemies = new Enemy[5][10];
    Timer timer;
    Long timeDelay, bulletDelay;
    Game game;

    public Board(Game game){
        this.game = game;
        setPreferredSize(new Dimension(924, 800));
        setBackground(Color.BLACK);
        timer = new Timer(1000/60, this);
        timer.start();

    }

    //Gives objects starting positions
    public void setup(){
        player = new Player(this);
        for(int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                enemies[row][col] = new Enemy(getWidth()/4 + col*50, row*50);
            }
        }
        bullets.add(new Bullet(player));
        timeDelay = System.currentTimeMillis();
        bulletDelay = System.currentTimeMillis();
    }

    public void checkCollisions(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        //player.moveLeft();
        if(game.isSpacePressed() && currentTime - bulletDelay >= 250){
            bullets.add(new Bullet(player));
            bulletDelay = System.currentTimeMillis();
        }

        for(int i = bullets.size()-1; i >= 0; i--){
            if(bullets.get(i).getY() < 0){
                bullets.remove(bullets.get(i));
            } else
                bullets.get(i).move();
        }



        System.out.println("Number of Bullets: " + bullets.size());

        if(currentTime - timeDelay >= 1000){
            for(int row = 0; row < 5; row++) {
                for (int col = 0; col < 10; col++) {
                    enemies[row][col].move();
                }
            }
            timeDelay = System.currentTimeMillis();
        }

        if(game.isLeftPressed() && player.getX() > 0 ){
            player.moveLeft();
        }
        if(game.isRightPressed() && player.getX() + player.getWIDTH() < getWidth()){
            player.moveRight();
        }


        repaint();
    }

    public void paintComponent(Graphics g){
        //System.out.println(System.currentTimeMillis());   shows current time in milliseconds
        super.paintComponent(g);

        player.paint(g);
        for(int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                enemies[row][col].paint(g);
            }
        }
        for(Bullet bullet: bullets){
            bullet.paint(g);
        }

    }


}
