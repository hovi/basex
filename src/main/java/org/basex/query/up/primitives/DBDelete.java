package org.basex.query.up.primitives;

import org.basex.core.cmd.*;
import org.basex.data.*;
import org.basex.query.func.*;
import org.basex.util.*;
import org.basex.util.list.*;

/**
 * Update primitive for the {@link Function#_DB_DELETE} function.
 *
 * @author BaseX Team 2005-12, BSD License
 * @author Christian Gruen
 */
public final class DBDelete extends UpdatePrimitive {
  /** Keys. */
  private final StringList paths = new StringList(1);
  /** Number of keys. */
  private int size;

  /**
   * Constructor.
   * @param d data
   * @param k entries to be deleted
   * @param ii input info
   */
  public DBDelete(final Data d, final String k, final InputInfo ii) {
    super(PrimitiveType.DBDELETE, -1, d, ii);
    paths.add(k);
    size = d.resources.binaries(k).size();
  }

  @Override
  public void merge(final UpdatePrimitive p) {
    for(final String path : ((DBDelete) p).paths) paths.add(path);
    size += p.size();
  }

  @Override
  public void apply() {
    for(final String path : paths) Delete.delete(data,  path);
  }

  @Override
  public int size() {
    return size;
  }
}
