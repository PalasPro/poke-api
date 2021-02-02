package com.palaspro.pokechallenge.datasource.room.dao;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.palaspro.pokechallenge.datasource.model.PokemonEntity;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PokemonDao_Impl implements PokemonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PokemonEntity> __insertionAdapterOfPokemonEntity;

  public PokemonDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPokemonEntity = new EntityInsertionAdapter<PokemonEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pokemon` (`id`,`name`,`category`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PokemonEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
      }
    };
  }

  @Override
  public Object insert(final List<PokemonEntity> pokemons, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPokemonEntity.insert(pokemons);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Flow<List<PokemonEntity>> pokemonList() {
    final String _sql = "SELECT * FROM pokemon";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"pokemon"}, new Callable<List<PokemonEntity>>() {
      @Override
      public List<PokemonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<PokemonEntity> _result = new ArrayList<PokemonEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PokemonEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item = new PokemonEntity(_tmpId,_tmpName,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
