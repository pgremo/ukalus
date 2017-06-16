/*
 * Created on May 13, 2004
 *  
 */
package ukalus.persistence.file;

import ukalus.persistence.Store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author gremopm
 * 
 */
public class FileStore implements Store {

	private File file;

	public FileStore(File file) {
		this.file = file;
	}

	public void store(Serializable object) throws IOException {
		File temp = new File(file.getAbsolutePath() + "-"
				+ System.currentTimeMillis());
		FileOutputStream fos = new FileOutputStream(temp);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(object);
		out.flush();
		fos.getFD().sync();
		out.close();
		file.delete();
		if (!temp.renameTo(file)) {
			throw new IOException("unable to rename " + temp + " to " + file);
		}
	}

	public Serializable load() throws IOException, ClassNotFoundException {
		Object result = null;
		if (file.exists()) {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(new FileInputStream(file));
				result = in.readObject();
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
		return (Serializable)result;
	}

	public void close() {
	}
}