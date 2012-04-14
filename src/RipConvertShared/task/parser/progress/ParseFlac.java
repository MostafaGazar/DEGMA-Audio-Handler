/*
    Copyright (C) 2005 - 2007 Mikael H�gdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package RipConvertShared.task.parser.progress;

import java.util.regex.Pattern;

/**
 * Parser for progress reading while reading flac data.
 */
public class ParseFlac extends ParserThread {
	/**
     *
     */
	public ParseFlac(boolean toStdout) {
		super(toStdout);
		aPattern = Pattern.compile(".*?: ?(\\d+)\\% complete");
		aMatchCount = 1;
	}
}
