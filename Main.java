package com.minesweeper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main implements Screen {

    private static final int rowL = 10;
    private static final int colL = 10;
    protected static final int WORLD_WIDTH = rowL * 50 + 300; //will change later due to difficulty
    protected static final int WORLD_HEIGHT = colL * 50; //will change later due to difficulty

    private SpriteBatch spriteBatch;    //useful to drawing graphics

    private ShapeRenderer shapeRenderer; //draws the shapes

    private Camera camera; //use to size the landscape

    private Viewport viewport;

    BitmapFont defaultFont = new BitmapFont();


    MineBoard board = new MineBoard(rowL,colL);

    boolean startPhase = false;
    boolean firstpressed = true;
    boolean victory = false;
    boolean gameOver = false;

    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,20);
        camera.update();

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void getMouseInput() {

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Location clickedLoc = board.mouseToBoardCoordinates(x, y);
            if(firstpressed) {
                firstpressed = false;
                startPhase = true;
                firstPress(clickedLoc);
            }
            board.clearSpace(clickedLoc);
            if(board.isGameOver(clickedLoc)) {
                gameOver = true;
            }
            //board.printBoard();
        }
        else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            Location clickedLoc = board.mouseToBoardCoordinates(x, y);
            if(firstpressed) {
                firstpressed = false;
                startPhase = true;
                firstPress(clickedLoc);
            }
            if(board.checkAvailableFlags() || board.checkIfFlagged(clickedLoc)) {
                board.flipStatus(clickedLoc);
            }
            //System.out.println(x + "," + y);
            //System.out.println(clickedLoc.getRow() + "," + clickedLoc.getCol());
            //board.printBoard();
        }
    }

    @Override
    public void render(float delta) {
        clearScreen();

        //all drawing of shapes MUST be in between begin/end
        shapeRenderer.begin();
        shapeRenderer.end();

        //all drawing of graphics MUST be in between begin/end
        spriteBatch.begin();
        spriteBatch.draw(board.getHappyFaceIcon(), WORLD_WIDTH - 200 , WORLD_HEIGHT / 2);
        spriteBatch.draw(board.getRedFlag(), WORLD_WIDTH - 200 , WORLD_HEIGHT / 2 + 150);
        defaultFont.draw(spriteBatch,"" + board.getFlagCounter() , WORLD_WIDTH - 160, WORLD_HEIGHT / 2 + 175);
        if(gameOver) {
            defaultFont.draw(spriteBatch, "You Died!", WORLD_WIDTH - 180, WORLD_HEIGHT / 2 - 20);
            spriteBatch.draw(board.getSadFaceIcon(), WORLD_WIDTH - 200 , WORLD_HEIGHT / 2);
            board.draw(spriteBatch);
        }
        else if(victory) {
            defaultFont.draw(spriteBatch, "You Won!", WORLD_WIDTH - 180, WORLD_HEIGHT / 2 - 20);
            spriteBatch.draw(board.getWinFaceIcon(), WORLD_WIDTH - 200 , WORLD_HEIGHT / 2);
            board.draw(spriteBatch);
        }
        else {
            board.draw(spriteBatch);
            getMouseInput();
            int counter = board.getSeenBoardCounter();
            if (counter >= rowL * colL * 2 - board.getMineCounter() * 2 + 1) {
                victory = true;
            }
        }

        spriteBatch.end();
    }



    //all of these leave alone
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }

     public void firstPress(Location startLoc) {
        board.createMines(startLoc);
        board.findMines();
     }
}
