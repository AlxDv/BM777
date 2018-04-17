/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author 12
 */
public class HojaSprites {
    private final int ancho;
    private final int alto;
    public final int[] pixeles;
    public HojaSprites(final String ruta,final int ancho, final int alto) throws IOException{
        this.ancho = ancho;
        this.alto = alto;
        pixeles = new int [ancho*alto];
    BufferedImage image = ImageIO.read(HojaSprites.class.getResource(ruta));
    image.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
    }
    public int getAncho(){
        return this.ancho;
    }
}
