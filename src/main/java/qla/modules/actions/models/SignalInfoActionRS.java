package qla.modules.actions.models;

import java.util.List;

import qla.modules.loganalyser.models.SignalModel;

public class SignalInfoActionRS extends AbstractActionCommand{
	private SignalModel signal;
	private List<SignalModel> signals;
	public SignalModel getSignal() {
		return signal;
	}
	public void setSignal(SignalModel signal) {
		this.signal = signal;
	}
	public List<SignalModel> getSignals() {
		return signals;
	}
	public void setSignals(List<SignalModel> signals) {
		this.signals = signals;
	}
}
