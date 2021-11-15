package br.edu.utfpr;

import br.edu.utfpr.enumeration.Rodada;
import br.edu.utfpr.jogo.Jogo;
import br.edu.utfpr.json.Dados;
import br.edu.utfpr.json.Questao;
import br.edu.utfpr.json.Resposta;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static br.edu.utfpr.jogo.Jogo.getJogo;

public class PararScreen implements Screen {
    private AssetManager assetManager;
    private ShowDoMilhao showDoMilhao;

    SpriteBatch batch;
    Texture img, showlogo;
    private Stage stage;
    public SacoMoeda sacoMoeda;

    Questao questao;
    Dados dados;
    Jogo jogo;
    private TextButton btnReiniciar;
    private BitmapFont font1 = new BitmapFont();
    // botoes respostas
    private Skin skinBotoesRespostas;

    float heightShape = 160;
    float font1Y = 280;




    public static PararScreen ref;

    public PararScreen(AssetManager assetManager, ShowDoMilhao showDoMilhao) {
        this.assetManager = assetManager;
        this.showDoMilhao = showDoMilhao;
    }


    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        ShowDoMilhao.addInputProcessor(stage);
        img = new Texture("imagens\\bg.jpg");

        //botoes respostas
        skinBotoesRespostas = assetManager.get("skin/neon-ui.json", Skin.class);
        jogo = getJogo();
        showlogo = assetManager.get("imagens/showlogo.png", Texture.class);
        ref = this;
        sacoMoeda = new SacoMoeda();

        sacoMoeda.setX((float) (Gdx.graphics.getWidth() / 1.35));
    }

    @Override
    public void render(float delta) {
        stage.act();

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        batch.draw(img, 0, 0);
        batch.draw(showlogo,270, Gdx.graphics.getHeight() - showlogo.getHeight()-20 );
        sacoMoeda.draw(batch, delta);

        font1.draw(batch, "Fim de Jogo\nPontuação: " + jogo.getPontuacao()+"\nVocê parou na "+jogo.getRodada().getLabel()+" / 16", 300, font1Y);
        font1.getData().setScale(1.8f, 1.8f);
        font1.setColor(Color.BLACK);

        btnReiniciar = new TextButton("Reiniciar", skinBotoesRespostas);
        this.btnReiniciar.setSize(120, 60);
        this.btnReiniciar.setPosition(500, 60, Align.center);
        this.btnReiniciar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               jogo.reiniciar();
               showDoMilhao.setGameScrean(new JogoScreen(assetManager, showDoMilhao));
                return true;
            }
        });
        stage.addActor(this.btnReiniciar);

        stage.draw();

        batch.end();

        ShapeRenderer shape = new ShapeRenderer();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        shape.rect(Gdx.graphics.getWidth() /2 - 200 , Gdx.graphics.getHeight() - heightShape - 230, Gdx.graphics.getWidth() /2.2f, heightShape);
        shape.end();
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
        img.dispose();
    }

}
