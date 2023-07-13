package myminiproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserServices {//5. aşama
    //Bu alan da yapilacak islemlerin kodlari yazildi ve list ile girilecek veriler islendi


    List<String> usernames = new ArrayList<String>();
    List<String> emails = new ArrayList<>();
    List<String> passwords = new ArrayList<>();


    public void register() {//6. aşama

        Scanner input = new Scanner(System.in);
        System.out.println("Ad-Soy ad giriniz:");
        String name = input.nextLine();


        String username;
        boolean existsUsername; //Listemizin icinde olan isimleri kontrol ederek True ve Fasle bilgisi verecek

        do {//Islemin en basta bir kere direkt olarak calismasi gerektiginden do while kullanmamiz gerek

            System.out.println("Kullanici Adi giriniz");
            username = input.nextLine();
            existsUsername = this.usernames.contains(username);//Contains kullanicidan alinan isimleri kontrol edip degiskene tasiyacak
            if (existsUsername) {
                System.out.println("Bu username kullanilmaktadir. Farkli bir username kullanin.");
            }
        } while (existsUsername);//existUsername uzerinden karsilastirma olacagindan sart kismina yazildi.

        String email;
        boolean existsEmail;
        boolean isValid; // Dogru yanliligi kontrol etmek icin bos bir boolean tanimladik
        do {
            System.out.println("Lutfen bir Email giriniz..");
            email = input.nextLine();
            isValid = validateEmail(email);


            existsEmail = this.emails.contains(email);//Daha once kayit yapilmismi kontrol edildi
            if (existsEmail) {
                System.out.println("Bu email kullanilmaktadir. Farkli bir email giriniz.");
                isValid = false;//break gibi islemi sonlandirmak icin kullanildi.
            }
        } while (!isValid);
//------------------------------------

        String password;//Password kontrolu icin bu satirdan sonrasi yazildi
        boolean isValidPassw;
        do {
            System.out.println("Sifrenizi Giriniz:");
            password = input.nextLine().trim();
            isValidPassw = validatePAssword(password);//validatePassword methode u ile password kontrol edildi.
        } while (!isValidPassw);

        User user = new User(name, username, email, password);//User isimli object tasarlandi ve bu object nin constructor de bulunacak bilgilerin calismasi icin variable'ler teker teker belirtildi
        //Normalde bu object i olusturmadan da cozebilirdik. Normalde kayıt yapilacak islemi direkt olarak user object'ini liste halinde tutada bilidik. Farkindalik icin yazildi.

        // bilgilerimizi kayıt etmemiz gerektigi icin this kewword unu kullanarak listelerimize, girilen bilgileri kayit ettirdik.

        this.usernames.add(user.username);
        this.emails.add(user.email);
        this.passwords.add(user.password);
        System.out.println("Tebrikler, isleminiz basariyla gerceklestirildi");
        System.out.println("Kullanici adi veya email ile sisteme giris yapabilirsiniz.");
    }

    public void login() {//7. aşama - Giris yapmasi icin login methode u olusturduk ve yapilacak islemleri yazdik. Geriye herhangi bir deger dondurmedigi icin void yazdik.

        Scanner input = new Scanner(System.in);//Kullanicidan veri alacagimiz icin Scanner object i olusturduk

        System.out.println("Kullanici adi veya email giriniz:");
        String userNameOrEmail = input.nextLine();

        //Kullanicinin girdigi degerinin email mi userName olup olmadigini kontrol etmek icin listeleri kontrol ediyoruz
        boolean isUserName = this.usernames.contains(userNameOrEmail);
        boolean isMail = this.usernames.contains(userNameOrEmail);

        if (isUserName || isMail) {//Bu sartlardan her hangi birisi kayitli veya kayitli degil ise diye kontrol ediyoruz

            boolean isWrong = true;//Girilen bilgilerin yanlis oldugu durumda dongu surekli calissin diye bu variable'i olusturduk
            while (isWrong) {
                System.out.println("Sifre giriniz");
                String password = input.nextLine();

                //username/email ile sifre ayni anda sisteme kayıt edildigi icin kayıtlı olan listelerdeki index sirasi ayni olacaktir. Bundan dolayi bu isleminde kontrolunu yapmamiz gerekmektedir.


                int index;
                if (isUserName) {
                    index = this.usernames.indexOf(userNameOrEmail);//username in index degerini elde ettik
                } else {
                    index = this.emails.indexOf(userNameOrEmail);//email in index degerini elde ettik
                }

                //password listesindeki index ile eslesmesi icin bu islemleri yapiyoruz
                if (this.passwords.get(index).equals(password)) {//this ile passwords u cagirdik ve get ile index variablenin degerini aldik. Equals ile password un esit olup olmadigini kontrol ettik.

                    System.out.println("Sisteme giriş yaptiniz. Hoşgeldiniz ");
                    isWrong = false;//Donguyu sonlandirmak icin yaptik

                } else {
                    System.out.println("Sifreniz yanlis, tekrar deneyiniz");
                }


            }


        } else {
            System.out.println("Kullanici kayitli degildir.");
            System.out.println("veya");
            System.out.println("Uyeiseniz bilgilerinizi kontrol ediniz, degiseniz uye olunuz!");
        }


    }


    public boolean validateEmail(String email) {//8. aşama - Email sartlarini kontrol etmesi icin yeni bir methode olusturduk
        boolean isValid; //Email i kontrol etmek icin bir variable tanimladik
        boolean space = email.contains(" ");//Contains ile bosluk olup olmadigini kontrol ettik
        boolean isContainAt = email.contains("@");//@ isareti kontol edildi
        if (space) {// Kullaniciyi uyarma if-else alani
            System.out.println("Email bosluk iceremez !");
            isValid = false; //eger bosluk icermis ise direkt olarak false verip islemi yeniledik.

        } else if (!isContainAt) {
            System.out.println("Email @ sembolu icermelidir");
            isValid = false;//eger @ icermemis ise direkt olarak false verip islemi yeniledik.

        } else {

            String firstPart = email.split("@")[0];//split ile email bilgisini @ isaretinden ayirdik ve 0 ve 1 indexli bir string olusturduk. 0'inci index'in verisini aldik
            String secondPart = email.split("@")[1];//@'den sonraki taraf icin

            int notValid = firstPart.replaceAll("[a-zA-Z0-9-._]", "").length();//aS1-_.*/<> Bu method ile sifrede olmasi muhtemel karakterleri kontrol edip yerine hiçlik koyduk
            boolean checkStart = notValid == 0;// notValid eger o ise True deger verecek                                                                                   //Eger olmamasini istemedigimiz karakterler var ise length() degerinin icinde bir deger olusacagindan int bir deger verecek

            boolean checkEnd = secondPart.equals("gmail.com") ||
                    secondPart.equals("hotmail.com") ||
                    secondPart.equals("yahoo.com");//mail isimlerinin 2. alaninin yani 1. index'inin buradaki karakterlere esitmi degil mi diye kontrol ettik

            if (!checkStart) {// Kullanici mail adresi yanlis girilmesi durumunda uyarildi
                System.out.println("Email Kullanici adi buyuk-kucuk harf, rakam, -,.,_ disinda karakter iceremez ! ");

            } else if (!checkEnd) {
                System.out.println("Email gmail.com, hotmail.com, yahoo.com ile bitmelidir. ");

            }
            isValid = checkEnd && checkStart; // Bu degiskenlerden birisi false olması durumunda isVaild false olacak
        }
        if (!isValid) {
            System.out.println("Gecersiz email, tekrar deneyin");
        }
        return isValid;
    }

    public boolean validatePAssword(String password) {//9. aşama - ayni islemleri password methodu olusturarak yaptik
        boolean isValid;
        String upperLetter = password.replaceAll("[^A-Z]", "");//A'dan Z'ye kadar olan buyuk harflerin disindaki karakterleri hiclikle degistirdik ve sadece buyuk harfler kaldi
        String lowerLetter = password.replaceAll("[^a-z]", "");//a'dan z'ye kadar olan kucuk harflerin disindaki karakterleri hiclikle degistirdik ve sadece buyuk harfler kaldi
        String digit = password.replaceAll("[^0-9]", "");//rakamlar disindakileri hiclikle degistirdik. Ek olarak ^0-9 yerine ' \\D ' yazarak da rakamlar disindakileri hiclikle degistirdik.
        String symbol = password.replaceAll("[\\P{Punct}]", "");//Semboller disindakileri hiclikle degistik

        boolean space = password.contains(" ");//Bosluk icerip icermedigini kontrol ettik
        boolean lenghtGt6 = password.length() >= 6;//password'un uzunlugunu en az 6 karakterle kontrol ettik
        //6 dan buyukse true degilse false olcak

        boolean existsUpper = upperLetter.length() > 0;//en az 1 buyuk harf i kontrol ettik lengt 0 dan buyukse true
        boolean existLower = lowerLetter.length() > 0;// en az 1 kucuk harfi kontrol etti
        boolean existsDigit = digit.length() > 0;//en az bir rakami kontrol etti
        boolean existsSymbol = symbol.length() > 0;//en az bir symbol u kontrol etti

        if (space) {//Bu alanda sifrelerde ki istenen sartlari kontrol ettik
            System.out.println("Sifre bosluk iceremez");

        } else if (!lenghtGt6) {
            System.out.println("Sifre en az 6 karakter icermelidir");

        } else if (!existLower) {
            System.out.println("Sifre en az bir kucuk harf icermeli");
        } else if (!existsUpper) {
            System.out.println("Sifre en az bir buyuk harf icermeli");
        } else if (!existsDigit) {
            System.out.println("Sifre en az bir rakam icermelidir");
        } else if (!existsSymbol) {
            System.out.println("Sifre en az bir karakter icermelidir");
        }
        isValid = !space && lenghtGt6 && existLower && existsUpper && existsDigit && existsSymbol;//Bu durumlardan birisi var ise false verecek
        if (!isValid) {//isValid false ise durumunu yazdik
            System.out.println("Gecersiz Password tekrar deneyiniz !");

        }
        return isValid;
    }
}

