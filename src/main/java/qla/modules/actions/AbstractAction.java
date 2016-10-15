package qla.modules.actions;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;

public abstract class AbstractAction<T extends AbstractActionCommand>  implements IAction<T>{
	@SuppressWarnings("rawtypes")
	private final Class rqClass;
	
	@SuppressWarnings("rawtypes")
	public AbstractAction(Class rqClass) {
		this.rqClass = rqClass;
	}
	
	@Override
	public AbstractActionCommand execute(T rq) throws ActionException {
		AbstractActionCommand result = proccess(rq);
		if(result != null && rq.getSuccessAction()!=null) {
			result.setSuccessAction(rq.getSuccessAction());
		}
		return result;
	}
	
	protected abstract AbstractActionCommand proccess(T rq) throws ActionException;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class getRqClass() {
		return rqClass;
	}
	
	
}
