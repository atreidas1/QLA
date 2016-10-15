package qla.modules.actions;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.actions.models.SavePropertyActionRQ;
import qla.modules.actions.models.SavePropertyActionRS;
import qla.modules.confuguration.AppConfiguration;

public class SavePropertyAction extends AbstractAction<SavePropertyActionRQ> {

	public SavePropertyAction(Class rqClass) {
		super(rqClass);
	}

	@Override
	public AbstractActionCommand proccess(SavePropertyActionRQ rq) throws ActionException {
		String key = rq.getKey();
		String value = rq.getValue();
		ActionHelper.checkStringForNullOrEmpty(key, "Key can not be null or empty!");
		AppConfiguration.updateProperty(key, value);
		SavePropertyActionRS rs = new SavePropertyActionRS();
		rs.setSuccess(String.format("Property %s updated succesfully!", key));
		return rs;
	}

}
