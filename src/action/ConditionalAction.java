package action;

public final class ConditionalAction extends AbstractAction {

	private AbstractAction action;

	private BooleanExpression expression;

	private ConditionalAction() {
	}

	@Override
	protected void action() {
		if (action == null) {
			throw new IllegalArgumentException("Indique a Ação que deve ser executada, utilize o método addAction.");
		}

		if (expression == null) {
			throw new IllegalArgumentException(
					"Indique a expressãoo condicional da Ação, utilize o método addConditional.");
		}

		if (expression.conditional()) {
			action.actionPerformed();
		}
	}

	public static ConditionalAction build() {
		return new ConditionalAction();
	}

	public ConditionalAction addAction(AbstractAction action) {
		this.action = action;
		return this;
	}

	public ConditionalAction addConditional(BooleanExpression expression) {
		this.expression = expression;
		return this;
	}

}
