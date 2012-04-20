/**
 * Created at: 04-09-2007
 */
package function.convert.util;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Uses <b>jl1.0.jar</b> to convert mp3 files to wav files.
 * 
 * @author Mostafa Gazar, eng.mostafa.gazar@gmail.com
 */
public class Mp3ToWav {

	public Mp3ToWav(String source, String dest) throws JavaLayerException {
		Converter aConverter = new Converter();
		aConverter.convert(source, dest);
	}
}
