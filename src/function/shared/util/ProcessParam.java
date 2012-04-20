/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser BaseSetupPanel Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser BaseSetupPanel Public License for more details.

    You should have received a copy of the GNU Lesser BaseSetupPanel Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package function.shared.util;

import java.util.ArrayList;

/**
 * Run ThradDump in background and dump.
 */
public class ProcessParam {
	private ArrayList<String> aParams = new ArrayList<String>();

	/**
     *
     */
	public ProcessParam() {
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param
	 */
	public void add(String param) {
		if (param.trim().length() > 0) {
			aParams.add(param.trim());
		}
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param
	 */
	public void addQuote(String param) {
		if (param.trim().length() > 0) {
			aParams.add("\"" + param.trim() + "\"");
		}
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param
	 */
	public void add(int param) {
		aParams.add(Integer.toString(param));
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param1
	 * @param param2
	 */
	public void add(String param1, String param2) {
		if (param1.trim().length() > 0 && param2.trim().length() > 0) {
			aParams.add(param1.trim());
			aParams.add(param2.trim());
		}
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param1
	 * @param param2
	 */
	public void add(String param1, int param2) {
		if (param1.trim().length() > 0) {
			aParams.add(param1.trim());
			aParams.add(Integer.toString(param2));
		}
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param1
	 * @param param2
	 *            String will be inside ""
	 */
	public void addQuote(String param1, String param2) {
		if (param1.trim().length() > 0 && param2.trim().length() > 0) {
			aParams.add(param1.trim());
			aParams.add("\"" + param2.trim() + "\"");
		}
	}

	/**
	 * Add param, but only if length is greater than 0.
	 * 
	 * @param param1
	 * @param param2
	 */
	public void addQuote(String param1, int param2) {
		if (param1.trim().length() > 0) {
			aParams.add(param1.trim());
			aParams.add("\"" + Integer.toString(param2) + "\"");
		}
	}

	/**
	 * Add param. Split strings into sub strings and add each one as seperate
	 * param.
	 * 
	 * @param param
	 */
	public void addSplitString(String param) {
		String ve[] = param.split(" ");
		for (String s : ve) {
			if (s.trim().length() > 0) {
				aParams.add(s.trim());
			}
		}
	}

	/**
	 * @return
	 */
	public String[] get() {
		String ve[] = new String[aParams.size()];
		int f = 0;
		for (String s : aParams) {
			ve[f++] = s;
		}
		return ve;
	}

	/**
	 * Get command string.
	 * 
	 * @param cmd
	 * @return
	 */
	@Override
	public String toString() {
		String tmp = "";
		for (String s : aParams) {
			tmp += s + " ";
		}
		return tmp;
	}
}
