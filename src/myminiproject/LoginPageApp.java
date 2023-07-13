package myminiproject;

import java.util.Scanner;

public class LoginPageApp {

    public static void main(String[] args) {


        start();


    }

    public static void start() {//1. aşama buranın iskeleti olusturuldu sonradan case'lere istedigimiz gorevler keyword ler ile atandi
        Scanner input = new Scanner(System.in);
        UserServices service = new UserServices();//Bu Class i aktif olarak kullanabilmek icin object olarak tanimladik ve register methode una ulastik
        int select;
        do {//3. aşama da islemleri do-while dongusunun icine koyduk
            showMenu();
            select = input.nextInt();


            switch (select) {//4. aşama

                case 1:
                    service.register();//ule ol
                    System.out.println(service.usernames);
                    System.out.println(service.emails);
                    System.out.println(service.passwords);//liste oldugu iicn direkt olarak sout un iicnde yazdirdik. Object olsaydi toString kullanmamiz gerekdi.
                    break;
                case 2:
                    service.login();//girisyap
                    break;
                case 0:
                    System.out.println("Cıkıs Yapiliyor. Iyi gunler dileriz");
                    break;
                default:
                    System.out.println("Hatali bir islem yaptiniz");

            }


        } while (select != 0);//select, yani secilen islem 0 a esit degilse dongu devam etsin
    }

    public static void showMenu() {//2. aşama - Burada sout ile once yazdigimiz code(74-78. satirlar arasi) lari secili bir sekilde sag tıklayip refactor de bulunan Extract Methode islemi ile start() methode unda calistirdik.
        System.out.println("======TECHPRO EDUCATİON======");
        System.out.println("1-Uye ol");
        System.out.println("2-giris yap");
        System.out.println("0-Cikis");
        System.out.println("Seçiminiz: ");
    }

}
