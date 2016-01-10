package floetteroed.opdyts.example;

import floetteroed.opdyts.SimulatorState;
import floetteroed.opdyts.searchalgorithms.Simulator;
import floetteroed.opdyts.trajectorysampling.TrajectorySampler;
import floetteroed.utilities.math.Matrix;
import floetteroed.utilities.math.Vector;

/**
 * 
 * @author Gunnar Flötteröd
 *
 */
public class LinearSystemSimulator implements Simulator<VectorDecisionVariable> {

	private final Matrix _A;

	private final Matrix _B;

	private Vector x = null;

	private Vector u = null;

	public LinearSystemSimulator(final Matrix _A, final Matrix _B) {
		this._A = _A.copy();
		this._B = _B.copy();
	}

	void setState(final Vector x) {
		this.x = x.copy();
	}

	void setDecisionVariable(final Vector u) {
		this.u = u.copy();
	}

	@Override
	public SimulatorState run(
			final TrajectorySampler<VectorDecisionVariable> evaluator) {
		return this.run(evaluator, null);
	}

	@Override
	public SimulatorState run(
			final TrajectorySampler<VectorDecisionVariable> evaluator,
			final SimulatorState initialState) {
		if (initialState != null) {
			this.x = ((VectorState) initialState).getX().copy();
		} else {
			this.x = new Vector(this._A.columnSize()); // x=0 is stat. for u=0
		}
		evaluator.initialize(); // sets this.u
		do {
			this.x = this._A.timesVectorFromRight(this.x);
			this.x.add(this._B.timesVectorFromRight(this.u));
			evaluator.afterIteration(new VectorState(this.x, this.u, this));
		} while (!evaluator.foundSolution());
		return new VectorState(this.x, this.u, this);
	}
}