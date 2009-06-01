package org.basex.query.ft;

import org.basex.query.IndexContext;
import org.basex.query.QueryContext;
import org.basex.query.QueryException;
import org.basex.query.item.FTNode;

/**
 * FTUnaryNot expression.
 *
 * @author Workgroup DBIS, University of Konstanz 2005-09, ISC License
 * @author Christian Gruen
 */
public final class FTNot extends FTExpr {
  /**
   * Constructor.
   * @param e expression
   */
  public FTNot(final FTExpr e) {
    super(e);
  }

  @Override
  public FTExpr comp(final QueryContext ctx) throws QueryException {
    return super.comp(ctx);
  }

  @Override
  public FTNode atomic(final QueryContext ctx) throws QueryException {
    final FTNode it = expr[0].atomic(ctx);
    it.score(it.score() == 0 ? 1 : 0);
    return it;
  }
  
  @Override
  public boolean indexAccessible(final IndexContext ic) throws QueryException {
    ic.ftnot ^= true;
    // in case of ftand ftnot seq could be set false in FTAnd
    ic.seq = true;
    final boolean ia = expr[0].indexAccessible(ic);
    ic.is = Integer.MAX_VALUE;
    return ia;
  }

  @Override
  public FTExpr indexEquivalent(final IndexContext ic) throws QueryException {
    return new FTNotIndex(expr[0].indexEquivalent(ic));
  }

  @Override
  public boolean usesExclude() {
    return true;
  }

  @Override
  public String toString() {
    return "ftnot " + expr[0];
  }
}
