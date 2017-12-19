package com.cascadiaoccidental.deadeye.util; /*
public class IsbnProcessor {
        public static String IsbnToAsin(String isbn) {

        }
        public static boolean validate(String isbn) {
            if(isbn.length() != 10 && isbn.length() != 13) return false;


        }
        private static char getIsbn13CheckDigit(char a[]) {

        }
        private static int validateIsbn13(char a[]) {
            int checksum = 0;
            int i, len,mul=1,sum=0, m10;
            len = a.length;
            if(len!=13)
                return -1;
            checksum = Character.getNumericValue(a[len-1]);
            for(i=0; i<len-1; i++)
            {
                sum += Character.getNumericValue(a[i]) * mul;
                if(mul==3) mul=1;
                else mul = 3;
            }
            m10 = sum%10;
            if( (10-m10) == checksum)
                return 1;
            else
                return 0;
        }
        private static int validateIsbn10(char a[]) {
            int checksum = 0;
            int i, len,pos=1,sum=0, m11;
            len = a.length;
            if(len!=10)
                return -1;
            checksum = Character.getNumericValue(a[len-1]);
            for( i=0,pos=1; i<len-1; i++,pos++)
                sum += Character.getNumericValue(a[i]) * pos;
            m11 = sum % 11;
            if(m11 == checksum)
                return 1;
            else
                return 0;
        }
}
*/
