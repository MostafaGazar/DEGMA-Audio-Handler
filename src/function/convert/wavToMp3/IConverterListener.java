/********************************************************************************
 *   Copyright 2007 SIP Response
 *   Copyright 2007 Michael D. Cohen
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ********************************************************************************/
package function.convert.wavToMp3;

public interface IConverterListener {
	// Status Codes
	public static final int CONVERTER_SUCCESS = 1;
	public static final int CONVERTER_FAILURE = 2;

	// Status Reason Codes
	public static final int CONVERTER_OK = 100;

	// Failure Reason Codes
	public static final int CONVERTER_FAILURE_IO_ERROR = 200;
	public static final int CONVERTER_FAILURE_PROCESSOR_CREATION = 201;
	public static final int CONVERTER_FAILURE_CONFIGURATION_TIMEOUT = 202;
	public static final int CONVERTER_FAILURE_TRACK_FORMAT = 203;
	public static final int CONVERTER_FAILURE_OUTPUT_FILE_CREATION = 204;
	public static final int CONVERTER_FAILURE_NOT_REALIZED = 205;
	public static final int CONVERTER_FAILURE_INTERRUPTED = 206;

	public void onConverterEvent(int status, int reason);
}
