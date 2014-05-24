/*
 *  PrintStreamCapturer.java
 *  
 *  This file is part of ARcowabungaproject.
 *  
 *  Bernabe Gonzalez Garcia <bernagonzga@gmail.com>
 *  Joaquim Dalmau Torva <jdalmaut@gmail.com>
 *  Marc Sabate Piñol <masapim@hotmail.com>
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *
 *   ARcowabungaproject is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   ARcowabungaproject is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with ARcowabungaproject.  If not, see <http://www.gnu.org/licenses/>. 
 */
package org.escoladeltreball.arcowabungaproject.server.gui.console;

import java.io.PrintStream;

import javax.swing.JTextArea;

public class PrintStreamCapturer extends PrintStream {

    // ====================
    // CONSTANTS
    // ====================

    // ====================
    // ATTRIBUTES
    // ====================

    /**
     * The output JTextArea to write
     */
    private JTextArea text;
    private boolean atLineStart;
    /**
     * The string to use like a prefix
     */
    private String indent;

    // ====================
    // CONSTRUCTORS
    // ====================

    /**
     * PrintStreamCapturer constructor
     * 
     * @param textArea
     *            the output textarea
     * @param capturedStream
     *            the stream to be captured
     * @param indent
     *            the string to be used as prefix
     */
    public PrintStreamCapturer(JTextArea textArea, PrintStream capturedStream,
	    String indent) {
	super(capturedStream);
	this.text = textArea;
	this.indent = indent;
	this.atLineStart = true;
    }

    /**
     * PrintStreamCapturer constructor
     * 
     * @param textArea
     *            the output textarea
     * @param capturedStream
     *            the stream to be captured
     */
    public PrintStreamCapturer(JTextArea textArea, PrintStream capturedStream) {
	this(textArea, capturedStream, "");
    }

    // ====================
    // PUBLIC METHODS
    // ====================

    // ====================
    // PROTECTED METHODS
    // ====================

    // ====================
    // PRIVATE METHODS
    // ====================

    private void writeToTextArea(String str) {
	if (text != null) {
	    synchronized (text) {
		text.setCaretPosition(text.getDocument().getLength());
		text.append(str);
	    }
	}
    }

    private void write(String str) {
	String[] s = str.split("\n", -1);
	if (s.length == 0)
	    return;
	for (int i = 0; i < s.length - 1; i++) {
	    writeWithPotentialIndent(s[i]);
	    writeWithPotentialIndent("\n");
	    atLineStart = true;
	}
	String last = s[s.length - 1];
	if (!last.equals("")) {
	    writeWithPotentialIndent(last);
	}
    }

    private void writeWithPotentialIndent(String s) {
	if (atLineStart) {
	    writeToTextArea(indent + s);
	    atLineStart = false;
	} else {
	    writeToTextArea(s);
	}
    }

    private void newLine() {
	write("\n");
    }

    // ====================
    // OVERRIDE METHODS
    // ====================

    @Override
    public void print(boolean b) {
	synchronized (this) {
	    super.print(b);
	    write(String.valueOf(b));
	}
    }

    @Override
    public void print(char c) {
	synchronized (this) {
	    super.print(c);
	    write(String.valueOf(c));
	}
    }

    @Override
    public void print(char[] s) {
	synchronized (this) {
	    super.print(s);
	    write(String.valueOf(s));
	}
    }

    @Override
    public void print(double d) {
	synchronized (this) {
	    super.print(d);
	    write(String.valueOf(d));
	}
    }

    @Override
    public void print(float f) {
	synchronized (this) {
	    super.print(f);
	    write(String.valueOf(f));
	}
    }

    @Override
    public void print(int i) {
	synchronized (this) {
	    super.print(i);
	    write(String.valueOf(i));
	}
    }

    @Override
    public void print(long l) {
	synchronized (this) {
	    super.print(l);
	    write(String.valueOf(l));
	}
    }

    @Override
    public void print(Object o) {
	synchronized (this) {
	    super.print(o);
	    write(String.valueOf(o));
	}
    }

    @Override
    public void print(String s) {
	synchronized (this) {
	    super.print(s);
	    if (s == null) {
		write("null");
	    } else {
		write(s);
	    }
	}
    }

    @Override
    public void println() {
	synchronized (this) {
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(boolean x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(char x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(int x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(long x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(float x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(double x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(char x[]) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(String x) {
	synchronized (this) {
	    print(x);
	    newLine();
	    super.println();
	}
    }

    @Override
    public void println(Object x) {
	String s = String.valueOf(x);
	synchronized (this) {
	    print(s);
	    newLine();
	    super.println();
	}
    }

    // ====================
    // GETTERS & SETTERS
    // ====================

}