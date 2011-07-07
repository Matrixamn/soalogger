/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmann.soa.logging.web.soaloggerweb.fileutil;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.regex.*;
/**
 *
 * @author KATVTUO
 */
public class JavaGrep {

        // Charset and decoder for ISO-8859-15
    private static Charset charset = Charset.forName("ISO-8859-15");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Pattern used to parse lines
    private static Pattern linePattern
	= Pattern.compile(".*\r?\n");

    // The input pattern that we're looking for
    private static Pattern pattern;

    // Compile the pattern from the command line
    //
    public static void compile(String pat) {
	try {
	    pattern = Pattern.compile(pat);
	} catch (PatternSyntaxException x) {
	    System.err.println(x.getMessage());
	    System.exit(1);
	}
    }

    // Use the linePattern to break the given CharBuffer into lines, applying
    // the input pattern to each line to see if we have a match
    //
    private static boolean grep(File f, CharBuffer cb) {
	Matcher lm = linePattern.matcher(cb);	// Line matcher
	Matcher pm = null;			// Pattern matcher
	int lines = 0;
        int occurrences = 0;
	while (lm.find()) {
	    lines++;
	    CharSequence cs = lm.group(); 	// The current line
	    if (pm == null)
		pm = pattern.matcher(cs);
	    else
		pm.reset(cs);
	    if (pm.find())
		occurrences++;
	    if (lm.end() == cb.limit())
		break;
	}
        if (occurrences > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Search for occurrences of the input pattern in the given file
    //
    public static boolean grep(File f) throws IOException {
        FileChannel fc = null;
        try {
	// Open the file and then get a channel from the stream
	FileInputStream fis = new FileInputStream(f);
	fc = fis.getChannel();

	// Get the file's size and then map it into memory
	int sz = (int)fc.size();
	MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

	// Decode the file into a char buffer
	CharBuffer cb = decoder.decode(bb);

	// Perform the search
	return grep(f, cb);
        } catch (Exception exp) {return false;} finally {
	// Close the channel and the stream
            try {
            fc.close();
            } catch (Exception e) {}
        }
    }

    
}
