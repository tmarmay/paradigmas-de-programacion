package parser;

import java.util.List;

/* Esta clase modela los atributos y metodos comunes a todos los distintos
 * tipos de parser existentes en la aplicacion
 */
public abstract class GeneralParser {
    protected String path;
    
    /* Constructor */
    public GeneralParser(String link) {
        this.path = link;
    }

    /* Abstract method ´parse´ */
    public abstract List<?> parse();
}