/**
 * Created by Vlad on 10.08.2016.
 */
public class Coder {
    String alphabet = "ЙjРЖkд9ЮZуoЫ~`ПXйSоМwБpClвmA.иефDV7>ьQЬЁЛy!@6n=<qWнЪ8Эг Ияh0+-FОхE#ubс1,кэЕB$Ц)ыRёGп2vбюT%Фцg5лiЩNHY^3Чeзъ:UСГJШт4c&fрIчM(шУKxДd/;*OжХщLzrЗPsЯаКtТaНмВА";
    char[] alphabetArray = alphabet.toCharArray();
    String coder(char[] wordToEncryptionArr, char[] codeWordArr) {
        int key = 0;
        int l;
        for (int z = 0; z < wordToEncryptionArr.length; z++) {
            l = z;
            while (l >= codeWordArr.length) l = l - codeWordArr.length;
            for (int u = 0; u < alphabetArray.length; u++) {
               if (codeWordArr[l] == alphabetArray[u]) {
                   key = u;
                   break;
               }
            }
            for (int k = 0; k < alphabetArray.length; k++) {
                if (wordToEncryptionArr[z] == alphabetArray[k]) {
                    if ((k+key) >= alphabetArray.length) {
                        wordToEncryptionArr[z] = alphabetArray[k + key - alphabetArray.length];
                        break;
                    } else {
                        wordToEncryptionArr[z] = alphabetArray[k + key];
                        break;
                    }
                }
            }
        }
        return String.valueOf(wordToEncryptionArr);
    }
    String decoder(char[] wordToDecryption, char[] codeWordArr) {
        int key = 0;
        int l;
        for (int z = 0; z < wordToDecryption.length; z++) {
            l = z;
            while (l >= codeWordArr.length) l = l - codeWordArr.length;
            for (int u = 0; u < alphabetArray.length; u++) {
                if (codeWordArr[l] == alphabetArray[u]) {
                    key = u;
                    break;
                }
            }
            for (int k = 0; k < alphabetArray.length; k++) {
                if (wordToDecryption[z] == alphabetArray[k]) {
                    if ((k - key) < 0) {
                        wordToDecryption[z] = alphabetArray[k - key + alphabetArray.length];
                        break;
                    } else {
                        wordToDecryption[z] = alphabetArray[k - key];
                        break;
                    }
                }
            }
        }
        return String.valueOf(wordToDecryption);
    }

    public String getRandomKey(int length) {
        String kye = "";
        for (int i = 0; i < length; i++) {
            kye = kye + alphabetArray[(int)(Math.random()*(alphabetArray.length) - 1)];
        }
        return kye;
    }
}