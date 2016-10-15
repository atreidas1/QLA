package qla.modules.actions;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.AnalyseLogActionRQ;
import qla.modules.actions.models.GetConfigurationActionRQ;
import qla.modules.actions.models.GetConfigurationActionRS;
import qla.modules.confuguration.AppConfiguration;

public class GetConfigurationAction extends AbstractAction<GetConfigurationActionRQ>{

	public GetConfigurationAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	public AbstractActionCommand proccess(GetConfigurationActionRQ rq) throws ActionException{
		Enumeration<String> properties = AppConfiguration.propertyNames();
		GetConfigurationActionRS rs = new GetConfigurationActionRS();
		List<Map<String, String>> props = new ArrayList<>();
		rs.setConfig(props);
		while (properties.hasMoreElements()) {
			String string = properties.nextElement();
			Map<String, String> prop = new HashMap<>();
			prop.put("key", string);
			prop.put("value", AppConfiguration.getProperty(string));
			props.add(prop);
		}
		rs.setAction(UIActions.SETTINGS_LOADED);
		return rs;
	}

}
