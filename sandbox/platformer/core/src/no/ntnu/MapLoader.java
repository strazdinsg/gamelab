package no.ntnu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class MapLoader implements Disposable {

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    final static float unitScale = 1;
    
    public MapLoader() {
        createRenderer();
    }
    
    public void render() {
        mapRenderer.render();
    }
    
    public void loadMap(String filepath) {
        if (map != null)
            map.dispose();
        
        if (mapLoader == null)
            mapLoader = new TmxMapLoader();
        
        map = mapLoader.load("Maps\\" + filepath);
        mapRenderer.setMap(map);
    }
    
    private void createRenderer() {
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        mapRenderer.setView((OrthographicCamera)MainClass.camera);
    }
    
    public TiledMap getMap(){
        return map;
    }
    
    @Override
    public void dispose() {
        map.dispose();
    }
}