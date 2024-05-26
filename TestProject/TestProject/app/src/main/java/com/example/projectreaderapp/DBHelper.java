package com.example.projectreaderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.projectreaderapp.model.CreateNews;
import com.example.projectreaderapp.model.ModelClass;
import com.example.projectreaderapp.model.SourceNews;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static String DB_NAME = "BKBNews.DB";
    private static int DB_VERSION = 1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;
    private static String createTableQuery = "Create Table UserAccount(IDUser INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", userName TEXT " +
            ", fullname TEXT " +
            ", email TEXT " +
            ", phone TEXT " +
            ", password TEXT " +
            ", image BLOB)";

    private static String createTableQuery1 = "Create Table AdminAccount(IDAdmin INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", UserName TEXT " +
            ", Password TEXT " +
            ", RoleAdmin TEXT)";

    private static String createTableQuery2 = "Create Table CategoryNews(IDCategory INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NameCategory TEXT)";

    private static String createTableQuery3 = "Create Table SourceNews(IDSourceNews INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NameSourceNews TEXT " +
            ", LogoSourceNews BLOB)";

    private static String createTableQuery4 = "CREATE TABLE News(IDNews INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NameNews TEXT " +
            ", ViewNews INTEGER " +
            ", ImageNews BLOB " +
            ", CategoryID INTEGER " +
            ", SourceNewsID INTEGER " +
            ", FOREIGN KEY('CategoryID') REFERENCES CategoryNews('IDCategory') " +
            ", FOREIGN KEY('SourceNewsID') REFERENCES SourceNews('IDSourceNews'))";

    private static String createTableQuery5 = "CREATE TABLE NewsDetail(IDNewsDetail INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NewsTitle TEXT " +
            ", NewsDetails1 TEXT " +
            ", NewsDetails2 TEXT " +
            ", ImageNewsDetails BLOB " +
            ", NewsID INTEGER, FOREIGN KEY('NewsID') REFERENCES News('IDNews'))";

    private static String createTableQuery6 = "CREATE TABLE Comment(IDComment INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", Comment TEXT " +
            ", NewsID INTEGER " +
            ", UserID INTEGER " +
            ", FOREIGN KEY('NewsID') REFERENCES News('IDNews') " +
            ", FOREIGN KEY('UserID') REFERENCES UserAccount('IDUser'))";

    private static String createTableQuery7 = "Create Table PriceGold(IDPriceGold INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NameCountry TEXT " +
            ", BuyPrice INTEGER " +
            ", SoldPrice INTEGER " +
            ", ImageCountry BLOB)";

    private static String createTableQuery8 = "Create Table Lottery(IDLottery INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", NameCountry TEXT " +
            ", Prize1 TEXT " +
            ", Prize2 TEXT " +
            ", Prize3 TEXT " +
            ", Prize4 TEXT " +
            ", Prize5 TEXT " +
            ", Prize6 TEXT " +
            ", Prize7 TEXT " +
            ", Prize8 TEXT " +
            ", PrizeS TEXT)";


    private static String ROW1 = "INSERT INTO " + "AdminAccount" + " ("
            + "UserName" + ", " + "Password" + ", "
            + "RoleAdmin" + ") Values ('admin','admin123','Admin')";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableQuery);
        db.execSQL(createTableQuery1);
        db.execSQL(createTableQuery2);
        db.execSQL(createTableQuery3);
        db.execSQL(createTableQuery4);
        db.execSQL(createTableQuery5);
        db.execSQL(createTableQuery6);
        db.execSQL(createTableQuery7);
        db.execSQL(createTableQuery8);
        db.execSQL(ROW1);
        Toast.makeText(context.getApplicationContext(), "Tạo bảng thành công", Toast.LENGTH_SHORT).show();
        Toast.makeText(context.getApplicationContext(), "Đã được thêm tài khoản Admin", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //Các hàm insert dữ liệu
    public void storeData(ModelClass modelClass){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = modelClass.getBtnimgv_user();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", modelClass.getEdttextusernameSignUp());
        contentValues.put("fullName", modelClass.getEdttextfullnameSignUp());
        contentValues.put("email", modelClass.getEdttextemailSignUp());
        contentValues.put("phone", modelClass.getEdttextphonenumberlSignUp());
        contentValues.put("password", modelClass.getEdttextpasswordSignUp());
        contentValues.put("image", imageInBytes);

        long checkIfQueryRun = db.insert("UserAccount", null, contentValues);
        if(checkIfQueryRun != -1){
            Toast.makeText(context.getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_LONG).show();
            db.close();
        }else{
            Toast.makeText(context.getApplicationContext(), "Đăng ký thất bại! Vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
    }

    public void addAdminAccount(String UserName, String Password, String RoleAdmin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UserName", UserName);
        cv.put("Password", Password);
        cv.put("RoleAdmin", RoleAdmin);
        long result = db.insert("AdminAccount",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm tài khoản admin thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm tài khoản admin thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNameCateNews(String NameCate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NameCategory", NameCate);
        long result = db.insert("CategoryNews",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm mục báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm mục báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addSourceNews(SourceNews sourceNews){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = sourceNews.getBtnimgv_logo_news();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("NameSourceNews", sourceNews.getSourceNewsName());
        cv.put("LogoSourceNews", imageInBytes);
        long result = db.insert("SourceNews",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm nguồn báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm nguồn báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNews(CreateNews createNews){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = createNews.getImageNews();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("NameNews", createNews.getNameNews());
        cv.put("CategoryID", createNews.getCategoryID());
        cv.put("SourceNewsID", createNews.getSourceNewsID());
        cv.put("ImageNews", imageInBytes);
        long result = db.insert("News",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDetailNews(String NewsTitle, String NewsDetails1, String NewsDetails2, Bitmap ImageNewsDetails, Integer NewsID){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = ImageNewsDetails;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("NewsTitle", NewsTitle);
        cv.put("NewsDetails1", NewsDetails1);
        cv.put("NewsDetails2", NewsDetails2);
        cv.put("ImageNewsDetails", imageInBytes);
        cv.put("NewsID", NewsID);

        long result = db.insert("NewsDetail",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm chi tiết báo thất bại!", Toast.LENGTH_SHORT).show();
            db.close();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm chi tiêt báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addComment(String Comment, Integer NewsID, Integer UserID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Comment", Comment);
        cv.put("NewsID", NewsID);
        cv.put("UserID", UserID);
        long result = db.insert("Comment",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm bình luận thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm bình luận thành công!", Toast.LENGTH_SHORT).show();
        }
    }


    public void addPriceGold(String NameCountry, Integer BuyPrice, Integer SoldPrice, Bitmap ImageCountry){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = ImageCountry;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("NameCountry", NameCountry);
        cv.put("BuyPrice", BuyPrice);
        cv.put("SoldPrice", SoldPrice);
        cv.put("ImageCountry", imageInBytes);
        long result = db.insert("PriceGold",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm giá vàng thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm giá vàng thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addLottery(String NameCountry,String PrizeS, String Prize1, String Prize2, String Prize3, String Prize4, String Prize5, String Prize6, String Prize7, String Prize8){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NameCountry", NameCountry);
        cv.put("PrizeS", PrizeS);
        cv.put("Prize1", Prize1);
        cv.put("Prize2", Prize2);
        cv.put("Prize3", Prize3);
        cv.put("Prize4", Prize4);
        cv.put("Prize5", Prize5);
        cv.put("Prize6", Prize6);
        cv.put("Prize7", Prize7);
        cv.put("Prize8", Prize8);
        long result = db.insert("Lottery",null,cv);
        if(result == -1){
            Toast.makeText(context.getApplicationContext(), "Thêm xổ số thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context.getApplicationContext(), "Thêm xổ số thành công!", Toast.LENGTH_SHORT).show();
        }
    }




    //Cac hàm truy xuất du liệu
    public Cursor readAllDataXosoAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  Lottery", null);
        }return cursor;

    }


    public Cursor readAllDataGiaVangAdmin(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  PriceGold", null);
        }return cursor;

    }

    public Cursor readAllDataComments(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  Comment, News, UserAccount WHERE NewsID = IDNews and UserID = IDUser", null);
        }return cursor;

    }

    public Cursor readDataComment(Integer IDNews){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  Comment, UserAccount  WHERE UserID=IDUser and NewsID = " + IDNews, null);
        }return cursor;

    }

    public Cursor readDataOneUser(String userName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  UserAccount  WHERE userName = " + userName, null);
        }return cursor;

    }

    public Cursor readAllDataNewsTrending(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and ViewNews >= 12", null);
        }return cursor;

    }

    public Cursor readAllDataNewsTrending1(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews order by ViewNews DESC", null);
        }return cursor;

    }


    public Cursor readAllDataNewsDetails(Integer IDNews){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  NewsDetail  WHERE NewsID = " + IDNews, null);
        }return cursor;

    }

    public Cursor readAllDataNewsLogoNews(Integer IDNews){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,SourceNews  WHERE SourceNewsID = IDSourceNews and IDNews = " + IDNews, null);
        }return cursor;

    }


    public Cursor readAllDataNews(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews", null);
        }return cursor;

    }

    public Cursor readAllDataNewsHot(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Hot'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsEntertainment(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Entertainment'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsLove(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Love'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsGlobal(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Global'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsCar(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Car'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsTechnology(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Technology'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsHealth(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'Health'", null);
        }return cursor;

    }

    public Cursor readAllDataNewsNew(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM  News,CategoryNews,SourceNews WHERE CategoryID = IDCategory and SourceNewsID = IDSourceNews and NameCategory = 'New'", null);
        }return cursor;

    }

    public Cursor readAllDataCateNews(){
        String query = "SELECT * FROM " + "CategoryNews";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }return cursor;

    }

    public Cursor readAllDataSourceNews(){
        String query = "SELECT * FROM " + "SourceNews";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }return cursor;

    }

     public Cursor readAllDataAdminAccount(){
        String query = "SELECT * FROM " + "AdminAccount";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }return cursor;

    }

    public Cursor readAllDataUserAccount(){
        String query = "SELECT * FROM " + "UserAccount";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }return cursor;
    }

    //Các hàm check theo ""
    public Boolean checkEmail(String EMail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserAccount where  email = ?", new String[] {EMail});
        if(cursor.getCount()>0)return true;
        else return false;
    }

    public Cursor getEmailUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserAccount where userName = email", new String[] {email});
        return cursor;
    }

    public Cursor getUser(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserAccount where userName = ?", new String[] {userName});
        return cursor;
    }
    public Boolean checkusername(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserAccount where  userName = ?", new String[] {userName});
        if(cursor.getCount()>0)return true;
        else return false;
    }
    public Boolean checkusernamepassword(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserAccount where  userName = ? and password = ?", new String[] {userName,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public Boolean checkAdminAccount(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from AdminAccount where UserName = ? and Password = ? and RoleAdmin = 'Admin'", new String[] {userName,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public Boolean checkEmployeeAccount(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from AdminAccount where UserName = ? and Password = ? and RoleAdmin = 'Employee'", new String[] {userName,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public Cursor getCateNews(String NameCategory){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from CategoryNews where NameCategory = ?", new String[] {NameCategory});
        return cursor;
    }
    public Boolean checkDetailsNews(Integer IDNews){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from NewsDetail where  NewsID = " + IDNews, null);
        if(cursor.getCount()>0)return true;
        else return false;
    }




    //Các hàm update
    public void updateLottery(String IDLottery,String NameCountry,String PrizeS, String Prize1, String Prize2, String Prize3, String Prize4, String Prize5, String Prize6, String Prize7, String Prize8){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NameCountry", NameCountry);
        cv.put("PrizeS", PrizeS);
        cv.put("Prize1", Prize1);
        cv.put("Prize2", Prize2);
        cv.put("Prize3", Prize3);
        cv.put("Prize4", Prize4);
        cv.put("Prize5", Prize5);
        cv.put("Prize6", Prize6);
        cv.put("Prize7", Prize7);
        cv.put("Prize8", Prize8);
        long result = db.update("Lottery", cv, "IDLottery=?",new String[]{IDLottery});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }



    void updateGiaVang(String IDPriceGold, String NameCountry, String BuyPrice, String SoldPrice, Bitmap ImageCountry){
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = ImageCountry;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("NameCountry", NameCountry);
        cv.put("BuyPrice", BuyPrice);
        cv.put("SoldPrice", SoldPrice);
        cv.put("ImageCountry", imageInBytes);

        long result = db.update("PriceGold", cv, "IDPriceGold=?",new String[]{IDPriceGold});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }


    public void updateComment(Integer IDComment, String Comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Comment", Comment);

        db.update("Comment", cv, "IDComment = " + IDComment,null);
    }



    void updateViewNews(Integer IDNews, Integer Views){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ViewNews", Views);

        db.update("News", cv, "IDNews = " + IDNews,null);
    }



    void updateAdminAccount(String IDAdmin, String UserName, String Password, String RoleAdmin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UserName", UserName);
        cv.put("Password", Password);
        cv.put("RoleAdmin", RoleAdmin);

        long result = db.update("AdminAccount", cv, "IDAdmin=?",new String[]{IDAdmin});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }

    void updateNews(String IDNews, String NameNews, Bitmap ImageNews, Integer CategoryID, Integer SourceNewsID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = ImageNews;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        cv.put("NameNews", NameNews);
        cv.put("ImageNews", imageInBytes);
        cv.put("CategoryID", CategoryID);
        cv.put("SourceNewsID", SourceNewsID);

        long result = db.update("News", cv, "IDNews=?",new String[]{IDNews});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }

    void updateNewsDetails(String IDNewsDetail, String NewsTitle, String NewsDetails1, String NewsDetails2, Bitmap ImageNewsDetails){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = ImageNewsDetails;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        cv.put("NewsTitle", NewsTitle);
        cv.put("NewsDetails1", NewsDetails1);
        cv.put("NewsDetails2", NewsDetails2);
        cv.put("ImageNewsDetails", imageInBytes);

        long result = db.update("NewsDetail", cv, "IDNewsDetail=?",new String[]{IDNewsDetail});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }

    void updateSourceNews(String IDSourceNews, String NameSourceNews, Bitmap LogoSourceNews){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Bitmap imageToStoreBitmap = LogoSourceNews;

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        cv.put("NameSourceNews", NameSourceNews);
        cv.put("LogoSourceNews", imageInBytes);

        long result = db.update("SourceNews", cv, "IDSourceNews=?",new String[]{IDSourceNews});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }

    void updateCategoryNews(String IDCategory, String NameCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NameCategory", NameCategory);

        long result = db.update("CategoryNews", cv, "IDCategory=?",new String[]{IDCategory});
        if(result==-1){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

        }
    }




    //Các hàm delete
    public void deleteLotteryOneRow(Integer IDLottery){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Lottery", "IDLottery = " + IDLottery,null);
        if(result == -1){
            Toast.makeText(context, "Xóa kết quả xổ số thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa kết quả xổ số thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteGiaVangOneRow(String IDPriceGold){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("PriceGold", "IDPriceGold=?",new String[]{IDPriceGold});
        if(result == -1){
            Toast.makeText(context, "Xóa giá vàng thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa giá vàng thành công!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteCommentOneRow(String IDComment){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Comment", "IDComment=?",new String[]{IDComment});
        if(result == -1){
            Toast.makeText(context, "Xóa bình luận thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa bình luận thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteUserAccountOneRow(String IDUser){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("UserAccount", "IDUser=?",new String[]{IDUser});
        if(result == -1){
            Toast.makeText(context, "Xóa tài khoản thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteAdminAccountOneRow(String IDAdmin){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("AdminAccount", "IDAdmin=?",new String[]{IDAdmin});
        if(result == -1){
            Toast.makeText(context, "Xóa tài khoản thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteNewsOneRow(String IDNews){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("News", "IDNews=?",new String[]{IDNews});
        if(result == -1){
            Toast.makeText(context, "Xóa báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteDetailsNewsOneRow(String IDNewsDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("NewsDetail", "IDNewsDetail=?",new String[]{IDNewsDetail});
        if(result == -1){
            Toast.makeText(context, "Xóa chi tiết báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa chi tiết báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteCateNewsOneRow(String IDCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("CategoryNews", "IDCategory=?",new String[]{IDCategory});
        if(result == -1){
            Toast.makeText(context, "Xóa mục báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa mục báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteSourceNewsOneRow(String IDSourceNews){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("SourceNews", "IDSourceNews=?",new String[]{IDSourceNews});
        if(result == -1){
            Toast.makeText(context, "Xóa nguồn báo thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa nguồn báo thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCommentOneRow(Integer IDComment){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Comment", "IDComment = " + IDComment,null);
        if(result == -1){
            Toast.makeText(context, "Xóa bình luận thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa bình luận thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteCommentAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "Comment");
    }

    void deleteAdminAccountAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "AdminAccount");
    }

    void deleteUserAccountAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "UserAccount");
    }

    void deleteNewsAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "News");
    }

    void deleteNewsDetailAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "NewsDetail");
    }

    void deleteCateNewsAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "CategoryNews");
    }

    void deleteSourceNewsAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "SourceNews");
    }

    void deleteGiaVangAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "PriceGold");
    }

    void deleteLotteryAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "Lottery");
    }
}
