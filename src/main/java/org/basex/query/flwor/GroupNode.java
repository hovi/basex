package org.basex.query.flwor;

import org.basex.query.*;
import org.basex.query.item.*;
import org.basex.util.*;

/**
 * GroupNode defines one valid partitioning setting.
 *
 * @author BaseX Team 2005-12, BSD License
 * @author Michael Seiferle
 */
final class GroupNode {
  /** Cached hash code. */
  private final int hash;
  /** List of grouping values. */
  final Value[] vals;

  /**
   * Creates a group node.
   * @param ii input info
   * @param vl grouping values
   * @throws QueryException query exception
   */
  GroupNode(final InputInfo ii, final Value[] vl) throws QueryException {
    vals = vl;
    int h = 0;
    for(final Value v : vals) h = (h << 5) - h + v.hash(ii);
    hash = h;
  }

  /**
   * Checks the nodes for equality.
   * @param c second group node
   * @return result of check
   * @throws QueryException query exception
   */
  boolean eq(final GroupNode c) throws QueryException {
    if(vals.length != c.vals.length) return false;
    for(int i = 0; i < vals.length; ++i) {
      final boolean it = vals[i].isItem();
      if(it ^ c.vals[i].isItem() || it &&
          !((Item) vals[i]).equiv(null, (Item) c.vals[i])) return false;
    }
    return true;
  }

  /**
   * Returns the hash code of this node.
   * @return hash code
   */
  public int hash() {
    return hash;
  }
}
