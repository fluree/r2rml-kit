package org.d2rq.db.expr;

import org.d2rq.db.types.DataType.GenericType;



public class Add extends BinaryOperator {

	public Add(Expression expr1, Expression expr2) {
		super(expr1, expr2, "+", true, GenericType.NUMERIC);
	}

	@Override
	protected Expression clone(Expression newOperand1, Expression newOperand2) {
		return new Add(newOperand1, newOperand2);
	}
}
