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
public class BonitaURLTools__Legacy__6_1 {
	public enum FormMode {
		app, form;
	}

	public static String APPLICATION_URL = "/bonita/console/homepage";

	/**
	 * @param protocol
	 *            Enter http, https, ...
	 * @param host
	 *            Enter your hostname
	 * @param port
	 *            Enter your port number
	 * @param activityInstanceId
	 * @param formMode
	 *            app or form
	 * @param apiAccessor
	 *            APIAccessor used to get details of task
	 * @return URL to access to a human task form
	 * @throws ProcessDefinitionNotFoundException
	 * @throws ActivityInstanceNotFoundException
	 */
	@Deprecated
	public static String getHumanTaskURL(String protocol, String host,
			long port, long activityInstanceId, FormMode formMode,
			APIAccessor apiAccessor) throws ActivityInstanceNotFoundException,
			ProcessDefinitionNotFoundException {
		return getHumanTaskURL(protocol, host, port, activityInstanceId,
				formMode, apiAccessor.getProcessAPI());
	}

	/**
	 * @param protocol
	 *            Enter http, https, ...
	 * @param host
	 *            Enter your hostname
	 * @param port
	 *            Enter your port number
	 * @param formName
	 *            a task form name is generated as
	 *            PROCESS_NAME--PROCESS_VERSION-TASK_NAME. Ex: Pool1--1.0--Step1
	 * @param activityInstanceId
	 * @param formMode
	 *            app or form
	 * @param processAPI
	 * @return URL to access to a human task form
	 * @throws ProcessDefinitionNotFoundException
	 * @throws ActivityInstanceNotFoundException
	 */
	@Deprecated
	public static String getHumanTaskURL(String protocol, String host,
			long port, long activityInstanceId, FormMode formMode,
			ProcessAPI processAPI) throws ActivityInstanceNotFoundException,
			ProcessDefinitionNotFoundException {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(protocol);
		buffer.append("://");
		buffer.append(host);
		buffer.append(":");
		buffer.append(port);
		buffer.append(APPLICATION_URL);
		buffer.append("?ui=form#form=");
		buffer.append(getHumanTaskFormName(activityInstanceId, processAPI));
		buffer.append("$entry&mode=");
		buffer.append(formMode.toString());
		buffer.append("&task=");
		buffer.append(activityInstanceId);

		return buffer.toString();
	}

	@Deprecated
	public static String getProcessURL(String protocol, String host, long port,
			long processDefinitionId, FormMode formMode, ProcessAPI processAPI)
			throws ProcessDefinitionNotFoundException {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(protocol);
		buffer.append("://");
		buffer.append(host);
		buffer.append(":");
		buffer.append(port);
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
	 * @param protocole
	 *            Enter http, https, ...
	 * @param host
	 *            Enter your hostname
	 * @param port
	 *            Enter your port number
	 * @param processDefinitionId
	 * @param formMode
	 *            app or form
	 * @param apiAccessor
	 *            APIAccessor used to get details of process
	 * @return URL to access to a process initialization form in autologin mode
	 * @throws ProcessDefinitionNotFoundException
	 */
	@Deprecated
	public static String getProcessURLWithAutologin(String protocol,
			String host, long port, long processDefinitionId,
			FormMode formMode, APIAccessor apiAccessor)
			throws ProcessDefinitionNotFoundException {

		return getProcessURLWithAutologin(protocol, host, port,
				processDefinitionId, formMode, apiAccessor.getProcessAPI());
	}

	/**
	 * @param protocol
	 *            Enter http, https, ...
	 * @param host
	 *            Enter your hostname
	 * @param port
	 *            Enter your port number
	 * @param processDefinitionId
	 * @param formMode
	 *            app or form
	 * @param processAPI
	 * 
	 * @return URL to access to a process initialization form in autologin mode
	 * @throws ProcessDefinitionNotFoundException
	 */
	@Deprecated
	public static String getProcessURLWithAutologin(String protocol,
			String host, long port, long processDefinitionId,
			FormMode formMode, ProcessAPI processAPI)
			throws ProcessDefinitionNotFoundException {
		String formName = getProcessFormName(processDefinitionId, processAPI);
		StringBuffer buffer = new StringBuffer("");
		buffer.append(protocol);
		buffer.append("://");
		buffer.append(host);
		buffer.append(":");
		buffer.append(port);
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