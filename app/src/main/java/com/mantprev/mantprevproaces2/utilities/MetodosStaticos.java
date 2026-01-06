package com.mantprev.mantprevproaces2.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MetodosStaticos {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    public static String fotosDeCierroOT = "No";
    public static String idOT  = "";
    public static String numOT = "";
    public static int idRepteEjecOT = 0;
    public static String nombreFotoMostrar = "";



    public static List<String> getFechaInicioAndFinalMesSelecc(String mesSelecc){
    /***********************************************************************/
        String[] dtsMesSelecc = mesSelecc.split("-");
        int intAnoSelecc = Integer.parseInt(dtsMesSelecc[0]);
        int intMesSelecc = Integer.parseInt(dtsMesSelecc[1]);

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, intAnoSelecc);
        calendario.set(Calendar.MONTH, intMesSelecc - 1);

        int primerDiaMes = 1;
        int ultimoDiaMes = calendario.getActualMaximum(Calendar.DATE);  //"yyyy-MM-dd"

        String dateStr1  = "01/" + intMesSelecc +"/"+ intAnoSelecc;
        String dateStr1A = intAnoSelecc +"-"+ intMesSelecc +"-01";

        String dateStr2  = ultimoDiaMes + "/" + intMesSelecc +"/"+ intAnoSelecc;
        String dateStr2A = intAnoSelecc +"-"+ intMesSelecc +"-"+ ultimoDiaMes;

        if(intMesSelecc < 10) {
            dateStr1  = "01/0" + intMesSelecc +"/"+ intAnoSelecc;
            dateStr1A = intAnoSelecc +"-0"+ intMesSelecc +"-01";

            dateStr2  = ultimoDiaMes + "/0" + intMesSelecc +"/"+ intAnoSelecc;
            dateStr2A = intAnoSelecc  + "-0" + intMesSelecc +"-"+ ultimoDiaMes;
        }

        List<String> fechasMesSelecc = new ArrayList<String>();
        fechasMesSelecc.add(dateStr1A);
        fechasMesSelecc.add(dateStr2A);

        return fechasMesSelecc;
    }

    public static String getMesFormater(int numDelMes){
    /*****************************************************/
        String mesFormated = "";

        switch (numDelMes) {
            case 1:
                mesFormated = "Enero";
                break;
            case 2:
                mesFormated = "Febrero";
                break;
            case 3:
                mesFormated = "Marzo";
                break;
            case 4:
                mesFormated = "Abril";
                break;
            case 5:
                mesFormated = "Mayo";
                break;
            case 6:
                mesFormated = "Junio";
                break;
            case 7:
                mesFormated = "Julio";
                break;
            case 8:
                mesFormated = "Agosto";
                break;
            case 9:
                mesFormated = "Septiembre";
                break;
            case 10:
                mesFormated = "Octubre";
                break;
            case 11:
                mesFormated = "Noviembre";
                break;
            case 12:
                mesFormated = "Diciembre";
        }

        return mesFormated;
    }

    public static int getNumeroDeMes(String nombreDelMes){
    /*****************************************************/
        int numeroMes = 0;

        switch (nombreDelMes) {
            case "Enero":
                numeroMes = 1;
                break;
            case "Febrero":
                numeroMes = 2;
                break;
            case "Marzo":
                numeroMes = 3;
                break;
            case "Abril":
                numeroMes = 4;
                break;
            case "Mayo":
                numeroMes = 5;
                break;
            case "Junio":
                numeroMes = 6;
                break;
            case "Julio":
                numeroMes = 7;
                break;
            case "Agosto":
                numeroMes = 8;
                break;
            case "Septiembre":
                numeroMes = 9;
                break;
            case "Octubre":
                numeroMes = 10;
                break;
            case "Noviembre":
                numeroMes = 11;
                break;
            case "Diciembre":
                numeroMes = 12;
        }

        return numeroMes;
    }


    public static Date getFechaDeMananaDt(){
        /*************************************/
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.add(Calendar.DAY_OF_MONTH, 1); //Le suma un dia a la fecha de hoy
        return fechaInicial.getTime(); //Retorna la fecha de hoy mas un dia adicional
    }

    public static String getFechaDeMananaStr(){
    /*************************************/
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.add(Calendar.DAY_OF_MONTH, 1); //Le suma un dia a la fecha de hoy
        Date fechaManana = fechaInicial.getTime();

        return dateFormat2.format(fechaManana);  // Devuelve yyyy-MM-yy
    }

    public static String getStrDateFormated(Date fechaIngr) {
    /************************************************/
        DateFormat formatFecha = DateFormat.getDateInstance(DateFormat.DEFAULT);
        return formatFecha.format(fechaIngr);
    }


    public static String getStrDateFormated (String strDate) {  //Recibe string "yyyy-MM-dd"
    /********************************************************/
        DateFormat formatFecha = DateFormat.getDateInstance(DateFormat.DEFAULT);

        Date fechaEjec = null;
        try {
            fechaEjec = dateFormat2.parse(strDate);  //14 Jun. 2025

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatFecha.format(fechaEjec);
    }


    public static String getStringFromDate (Date fechaDate) {
    /******************************************************/
        return dateFormat2.format(fechaDate);  //yyyy-MM-yy
    }


    public static Date getFechaUltimos30Dias() {
    /********************************************/
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.add(Calendar.DAY_OF_MONTH, -30); // Last 30 days from today
        Date fecha1 = fechaInicial.getTime();
        Date fechaSinHora = null;

        try {
            fechaSinHora = dateFormat.parse(dateFormat.format(fecha1));  // ---> "dd/MM/yyyy"

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaSinHora; // Para que la busqueda empiece desde las 0:0 horas del dia seleccionado
    }


    public static Date getDateFromString (String fechaString) {  // yyyy-MM-dd
    /*******************************************************/
        Date fechaDate = null;
        try {
            fechaDate = dateFormat2.parse(fechaString);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return fechaDate;
    }


    public static String getStrDateProxs45dias() {
    /***************************************/
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.add(Calendar.DAY_OF_MONTH, 45); //Proximos 45 days from today
        Date fecha1 = fechaInicial.getTime();

        return dateFormat2.format(fecha1);
    }

    public static Date getFechaUltimos365Dias() {
    /********************************************/
        Calendar fechaInicial = Calendar.getInstance();
        fechaInicial.add(Calendar.DAY_OF_MONTH, -365); // Last 365 days from today
        Date fecha1 = fechaInicial.getTime();
        Date fechaSinHora = null;

        try {
            fechaSinHora = dateFormat.parse(dateFormat.format(fecha1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaSinHora; // Para que la busqueda empiece desde las 0:0 horas del dia seleccionado
    }


    public static String getFechaStrFormated(String fechaStr) {
    /**********************************************************/
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = null;
        try {
            fecha = formato.parse(fechaStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String strDateFormated = dateFormat.format(fecha);

        return strDateFormated;
    }


    public static String getUltimoDiaMes(String mesDelRepte) {  //Ejemplo: 05-2022
    /*********************************************************/
        String mesRepte = "01/" + mesDelRepte.replace("-", "/");

        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            fecha.setTime(sdf.parse(mesRepte));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        fecha.add(Calendar.MONTH, 1);
        fecha.set(Calendar.DATE, 1);
        fecha.add(Calendar.DATE, -1);

        int lastDayOfMonth = fecha.get(Calendar.DAY_OF_MONTH);

        String ultDiaMes = "";
        if (lastDayOfMonth < 10) {
            ultDiaMes = "0" + lastDayOfMonth;
        } else {
            ultDiaMes = Integer.toString(lastDayOfMonth);
        }

        return  ultDiaMes + "/" + mesDelRepte.replace("-", "/");
    }

    //*****************************************************


    public static File getFile(Context context, Uri uri) throws IOException {
    /***********************************************************************/
        File destinationFile = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));

        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(inputStream, destinationFile);

        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFile;
    }

    public static void createFileFromStream(InputStream inputStream, File destinationFile) {
    /***************************************************************************/
        try (OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[4096];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();

        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }


    private static String queryName(Context context, Uri uri) {
    /*********************************************************/
        Cursor returnCursor =  context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }






}
