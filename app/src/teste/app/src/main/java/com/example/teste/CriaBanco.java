@Override
public void onCreate(SQLiteDatabase db) {
    String sql1 = "CREATE TABLE usuarios (" +
            "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT, email TEXT, senha TEXT)";

    String sql2 = "CREATE TABLE alimentacao (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_usuario INTEGER," +
            "tipo TEXT," +
            "data TEXT," +
            "observacao TEXT)";

    db.execSQL(sql1);
    db.execSQL(sql2);
}
