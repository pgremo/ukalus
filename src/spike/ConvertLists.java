
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Created on Feb 24, 2005
 *
 */

/**
 * @author gremopm
 *  
 */
public final class ConvertLists {

  private ConvertLists() {

  }

  public static void main(String[] args) throws Exception {
    File inDir = new File("src/resource/wordlists");
    File outDir = new File("work");
    outDir.mkdirs();
    File[] files = inDir.listFiles();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        SortedSet words = new TreeSet();
        Reader in = null;
        PrintWriter out = null;
        try {
          in = new BufferedReader(new FileReader(files[i]));
          StreamTokenizer tokenizer = new StreamTokenizer(in);

          while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_WORD
                && tokenizer.sval.length() > 1) {
              words.add(tokenizer.sval.toLowerCase());
            }
          }

          out = new PrintWriter(new FileWriter(new File(outDir,
            files[i].getName())));
          Iterator iterator = words.iterator();
          while (iterator.hasNext()) {
            out.println((String) iterator.next());
          }
        } finally {
          if (in != null) {
            in.close();
          }
          if (out != null) {
            out.close();
          }
        }
      }
    }

  }
}