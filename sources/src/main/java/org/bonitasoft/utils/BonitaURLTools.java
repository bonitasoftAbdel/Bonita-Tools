package org.bonitasoft.utils;

import org.bonitasoft.engine.api.APIAccessor;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.flownode.ActivityInstance;
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;

/**
 * @author Yhon Acurio
 * @see <a
 *      href="http://documentation.bonitasoft.com/accessing-bonita-bpm-portal-and-forms-url">Accessing
 *      Bonita BPM Portal and forms by URL </a>
 */
public class BonitaURLTools {
	public enum FormMode {
		app, form;
	}

	public static String APPLICATION_URL = "/bonita";

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param activityInstanceId
	 * @param formMode
	 *            app or form
	 * @param processAPI
	 * @return URL to access to a human task form
	 * @throws ProcessDefinitionNotFoundException
	 * @throws ActivityInstanceNotFoundException
	 */

	private static String getHumanTaskURL(String serverURL,
			long activityInstanceId, FormMode formMode, ProcessAPI processAPI)
			throws ActivityInstanceNotFoundException,
			ProcessDefinitionNotFoundException {
		StringBuffer buffer = new StringBuffer(serverURL);
		buffer.append(APPLICATION_URL);
		buffer.append("?ui=form#form=");
		buffer.append(getHumanTaskFormName(activityInstanceId, processAPI));
		buffer.append("$entry&mode=");
		buffer.append(formMode.toString());
		buffer.append("&task=");
		buffer.append(activityInstanceId);
		buffer.append("&assignTask=true");

		return buffer.toString();
	}

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param activityInstanceId
	 * 
	 * @param apiAccessor
	 *            APIAccessor used to get details of task
	 * @return URL to access to a human task form in APP mode
	 * @throws ProcessDefinitionNotFoundException
	 * @throws ActivityInstanceNotFoundException
	 */
	public static String getHumanTaskURL(String serverURL,
			long activityInstanceId, APIAccessor apiAccessor)
			throws ActivityInstanceNotFoundException,
			ProcessDefinitionNotFoundException {
		return getHumanTaskURL(serverURL, activityInstanceId, FormMode.app,
				apiAccessor.getProcessAPI());
	}

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param processDefinitionId
	 * @param formMode
	 *            app or form
	 * @param processAPI
	 * @return URL to access to a process initialization form
	 * @throws ProcessDefinitionNotFoundException
	 */
	private static String getStartProcessCaseURL(String serverURL,
			long processDefinitionId, FormMode formMode, ProcessAPI processAPI)
			throws ProcessDefinitionNotFoundException {
		StringBuffer buffer = new StringBuffer(serverURL);
		buffer.append(APPLICATION_URL);
		buffer.append("?ui=form&form=");
		buffer.append(getProcessFormName(processDefinitionId, processAPI));
		buffer.append("$entry&mode=");
		buffer.append(formMode.toString());
		buffer.append("&process=");
		buffer.append(processDefinitionId);
		return buffer.toString();
	}

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param processDefinitionId
	 * @param apiAccessor
	 *            APIAccessor used to get details of process
	 * @return URL to access to a process initialization form in APP mode
	 * @throws ProcessDefinitionNotFoundException
	 */
	public static String getStartProcessCaseURL(String serverURL,
			long processDefinitionId, APIAccessor apiAccessor)
			throws ProcessDefinitionNotFoundException {
		return getStartProcessCaseURL(serverURL, processDefinitionId,
				FormMode.app, apiAccessor.getProcessAPI());
	}

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param processDefinitionId
	 * 
	 * @param apiAccessor
	 *            APIAccessor used to get details of process
	 * @return URL to access to a process initialization form in autologin and
	 *         APP mode
	 * @throws ProcessDefinitionNotFoundException
	 */
	public static String getStartProcessCaseURLWithAutologin(String serverURL,
			long processDefinitionId, APIAccessor apiAccessor)
			throws ProcessDefinitionNotFoundException {

		return getStartProcessCaseURLWithAutologin(serverURL,
				processDefinitionId, FormMode.app, apiAccessor.getProcessAPI());
	}

	/**
	 * @param serverURL
	 *            Bonita server host URL
	 * 
	 *            Examples: http://localhost:8080 or http://localhost or
	 *            https://localhost:8081 *
	 * @param processDefinitionId
	 * @param formMode
	 *            app or form
	 * @param processAPI
	 * 
	 * @return URL to access to a process initialization form in autologin mode
	 * @throws ProcessDefinitionNotFoundException
	 */
	private static String getStartProcessCaseURLWithAutologin(String serverURL,
			long processDefinitionId, FormMode formMode, ProcessAPI processAPI)
			throws ProcessDefinitionNotFoundException {
		String formName = getProcessFormName(processDefinitionId, processAPI);
		StringBuffer buffer = new StringBuffer(serverURL);
		buffer.append(APPLICATION_URL);
		buffer.append("?ui=form&autologin=");
		buffer.append(formName);
		buffer.append("#form=");
		buffer.append(formName);
		buffer.append("$entry&mode=");
		buffer.append(formMode.toString());
		buffer.append("&process=");
		buffer.append(processDefinitionId);
		return buffer.toString();
	}

	private static String getHumanTaskFormName(long activityInstanceId,
			ProcessAPI processAPI) throws ActivityInstanceNotFoundException,
			ProcessDefinitionNotFoundException {
		ActivityInstance activityInstance = processAPI
				.getActivityInstance(activityInstanceId);

		return getProcessFormName(activityInstance.getProcessDefinitionId(),
				processAPI) + "--" + activityInstance.getName();
	}

	private static String getProcessFormName(long processDefinitionId,
			ProcessAPI processAPI) throws ProcessDefinitionNotFoundException {
		ProcessDefinition processDefinition = processAPI
				.getProcessDefinition(processDefinitionId);
		return processDefinition.getName() + "--"
				+ processDefinition.getVersion();
	}
}