package com.mantprev.mantprevproaces2.utilities;

import com.mantprev.mantprevproaces2.ModelosDTO1.RustEquiposDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static String numRutina = "";
    public static String semanaRuts = "";
    public static int idRutEquip = 0;
    public static int idEquipo = 0;
    public static int idRepteEjecOT = 0;
    public static int idRepteEjecRut = 0;
    public static String nombreFotoMostrar = "";


    public static String getDiaInicioSemSelecc(String semSelecc){
    /***********************************************************/
        String[] dtsWeekSelecc = semSelecc.split("-");
        int intAnoSelecc = Integer.parseInt(dtsWeekSelecc[0]);
        int intWeekSelec = Integer.parseInt(dtsWeekSelecc[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // Configurar el año y la semana del año
        calendar.set(Calendar.YEAR, intAnoSelecc);
        calendar.set(Calendar.WEEK_OF_YEAR, intWeekSelec);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date fechaPrimerDiaSem = calendar.getTime();
        String fechaString = dateFormat2.format(fechaPrimerDiaSem);  //yyyy-MM-yy
        String formatDateSt = getStrDateFormated(fechaString);       //14 Jun. 2025

        return formatDateSt.substring(0, 6);   //14 Jun.
    }

    public static String getDiaFinalSemSelecc(String semSelecc){
    /***********************************************************/
        String[] dtsWeekSelecc = semSelecc.split("-");
        int intAnoSelecc = Integer.parseInt(dtsWeekSelecc[0]);
        int intWeekSelec = Integer.parseInt(dtsWeekSelecc[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // Configurar el año y la semana del año
        calendar.set(Calendar.YEAR, intAnoSelecc);
        calendar.set(Calendar.WEEK_OF_YEAR, intWeekSelec);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE,6);
        Date fechaUltimoDiaSem = calendar.getTime();

        String fechaString = dateFormat2.format(fechaUltimoDiaSem);  //yyyy-MM-yy
        String formatDateSt = getStrDateFormated(fechaString);       //14 Jun. 2025

        return formatDateSt.substring(0, 6);   //14 Jun.
    }

    public static List<String> getFechaInicioAndFinalMesSelecc(String mesSelecc){
    /***********************************************************************/
        String[] dtsMesSelecc = mesSelecc.split("-");
        int intAnoSelecc = Integer.parseInt(dtsMesSelecc[0]);
        int intMesSelecc = Integer.parseInt(dtsMesSelecc[1]);

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, intAnoSelecc);
        calendario.set(Calendar.MONTH, intMesSelecc - 1);

        int ultimoDiaMes = calendario.getActualMaximum(Calendar.DATE);  //"yyyy-MM-dd"

        String dateStr1 = intAnoSelecc +"-"+ intMesSelecc +"-01";
        String dateStr2 = intAnoSelecc +"-"+ intMesSelecc +"-"+ ultimoDiaMes;

        if(intMesSelecc < 10) {
            dateStr1 = intAnoSelecc +"-0"+ intMesSelecc +"-01";
            dateStr2 = intAnoSelecc  + "-0" + intMesSelecc +"-"+ ultimoDiaMes;
        }

        List<String> fechasMesSelecc = new ArrayList<String>();
        fechasMesSelecc.add(dateStr1);
        fechasMesSelecc.add(dateStr2);

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
        fechaInicial.clear();
        //fechaInicial.add(Calendar.DAY_OF_MONTH, 1); //Le suma un dia a la fecha de hoy
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

        return formatFecha.format(fechaEjec);   //14 Jun. 2025
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

    public static int getCantidadRutsConRepteEjec(List<RustEquiposDTO> listaRutsEquips){
    /**********************************************************************************/
        int cantRutinas = listaRutsEquips.size();
        int cantRutsReptds = 0;

        for (int i=0; i<cantRutinas; i++){
            int idRepEjc = listaRutsEquips.get(i).getIdRepteEjec();
            if (idRepEjc > 0){
                cantRutsReptds ++;
            }
        }
        return cantRutsReptds;
    }





}
