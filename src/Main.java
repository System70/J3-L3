
import java.io.*;
import java.util.*;

public class Main {

    final static int PAGE_SIZE = 1800;

    public static void main(String[] args) {

        // 1. Read the file (approx. 50 bytes long) into byte array & display it in console
        File file = new File("file11.txt");
        byte[] array1 = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(array1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < array1.length; i++) {
            System.out.print((char) array1[i]);
        }
        System.out.println();


        // 2. Sequentially unite 5 files (approx. 100 bytes long) into single one
        // Assume files are file21.txt, file22.txt, file23.txt, file24.txt, file25.txt
        ArrayList<FileInputStream> alfis = new ArrayList<>();
        String fileName = "file2?.txt";
        for (int i = 1; i < 6; i++) {
            try {
                alfis.add(new FileInputStream(fileName.replace('?', (char) (i + 48))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int b, n = 0;
        try (SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(alfis));
            FileOutputStream fos = new FileOutputStream("file5.txt");) {
            while ((b = sis.read()) != -1) {
                fos.write(b);
                n++;
            }
            System.out.println("Written bytes: " + n);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 3. Create console application to read text from file (> 10 MB in size) page by page
        // (page size 1800 characters) & show given page in console. Read time must be less than 10 sec.,
        // File 06perms.txt taken from ftp.gr.debian.org/pub/lang/perl/modules/06perms.txt
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        int pageNumber = 0;
        byte[] buffer = new byte[PAGE_SIZE];
        try (RandomAccessFile raf = new RandomAccessFile("06perms.txt", "r")) {
            while (true) {
                System.out.println("Enter page number: (-1 to exit): ");
                userInput = sc.nextLine();
                if (userInput.equals("-1"))
                    break;
                try {
                    pageNumber = Integer.parseInt(userInput);
                } catch (Exception e) {
                    System.out.println("Incorrect input!");
                    continue;
                }
                raf.seek((pageNumber - 1) * PAGE_SIZE);
                raf.read(buffer);

                for (int i = 0; i < buffer.length; i++)
                    System.out.print((char) buffer[i]);
                System.out.println();
            }
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.close();
    }



}
