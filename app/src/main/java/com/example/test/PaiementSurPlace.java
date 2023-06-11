package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

public class PaiementSurPlace extends AppCompatActivity {
    Button ticket;

    File pdfFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement_sur_place);

        ticket=findViewById(R.id.btnOuvrir);

        try {
            pdfFile = createPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final File finalPdfFile = pdfFile;
        ticket.setOnClickListener(view -> {
            Uri pdfUri = FileProvider.getUriForFile(PaiementSurPlace.this, getApplicationContext().getPackageName() + ".fileprovider", finalPdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });
    }



    private File createPdf() throws  FileNotFoundException{
        Intent data= getIntent();
        String parkingName = data.getStringExtra("ParkingName");
        String parkingWilaya = data.getStringExtra("ParkingWilaya");
        String nameUser=data.getStringExtra("UserName");
        String matricule =data.getStringExtra("MATRIC");
        String tarifH=data.getStringExtra("TARIF_H");
        String tarif= data.getStringExtra("TARIF_TOTAL");
        String dateDebutReservation =data.getStringExtra("DATE_DebutRes");
        String heureDebutReservation=data.getStringExtra("HOUR_DebutRes");
        String nbrj=data.getStringExtra("NBR_JOURS");
        String  nbrh=data.getStringExtra("NBR_HEURS");



        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "factureBlasti.pdf");
      //  OutputStream outputStream = new FileOutputStream(file);


        PdfWriter pdfWriter =new PdfWriter(file);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        Document document =new Document(pdfDocument);

        DeviceRgb blue1 = new DeviceRgb(	26, 115, 232);
        DeviceRgb gray10 = new DeviceRgb(	230, 230, 230);


        float columnWidth[]={140,140,140,140};
        Table table1= new Table(columnWidth);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);



        Random random = new Random();
        int randomNumber = random.nextInt(1000000);



        //Tab1  01
        Drawable d=getDrawable(R.drawable.img_1);
        Bitmap bitmap=((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData =stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image =new Image(imageData);
        image.setHeight(140);
        image.setWidth(140);
        image.setHorizontalAlignment(HorizontalAlignment.LEFT);


        table1.addCell(new Cell(3,1).add(image).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(1,2).add(new Paragraph("Facture").setFontSize(26).setBold().setFontColor(blue1)).setBorder(Border.NO_BORDER));
//      table1.addCell(new Cell().add(new Paragraph("")));
        //Tab1  02
//       table1.addCell(new Cell().add(new Paragraph("")));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("facture N: ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+randomNumber)).setBorder(Border.NO_BORDER));
        //Tab1  03
//      table1.addCell(new Cell().add(new Paragraph("")));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("date:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+day+ "/" + month + "/" +year )).setBorder(Border.NO_BORDER));
        //Tab1  04
        table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        //Tab1  05
        table1.addCell(new Cell().add(new Paragraph("Vers")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        //Tab1  06
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Nom du client:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+nameUser)).setBorder(Border.NO_BORDER));
        //Tab1  07
        table1.addCell(new Cell().add(new Paragraph(""+parkingName)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Matricule:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+matricule)).setBorder(Border.NO_BORDER));
        //Tab1  08
        table1.addCell(new Cell().add(new Paragraph(""+parkingWilaya)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Début de réservation:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+dateDebutReservation)).setBorder(Border.NO_BORDER));
        //Tab1  09
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("à")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(""+heureDebutReservation)).setBorder(Border.NO_BORDER));
        //Tab1  10
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Payement:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph(" Sur Place")).setBorder(Border.NO_BORDER));


        float columnWidth2[]={140,84,84,84,84,84};
        Table table2 =new Table(columnWidth2);

        //Tab2  01
        table2.addCell(new Cell().add(new Paragraph("Nom de parking").setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        table2.addCell(new Cell().add(new Paragraph("places").setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        table2.addCell(new Cell().add(new Paragraph("Jours").setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        table2.addCell(new Cell().add(new Paragraph("Heures").setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        table2.addCell(new Cell().add(new Paragraph("Tarif/H").setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        table2.addCell(new Cell().add(new Paragraph("Total").setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1));
        //Tab2  02
        table2.addCell(new Cell().add(new Paragraph(""+parkingName)).setBackgroundColor(gray10));
        table2.addCell(new Cell().add(new Paragraph("1")).setBackgroundColor(gray10));
        table2.addCell(new Cell().add(new Paragraph(""+nbrj)).setBackgroundColor(gray10));
        table2.addCell(new Cell().add(new Paragraph(""+nbrh)).setBackgroundColor(gray10));
        table2.addCell(new Cell().add(new Paragraph(""+tarifH)).setBackgroundColor(gray10));
        table2.addCell(new Cell().add(new Paragraph(""+tarif)).setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(blue1);



        document.add(table1);
        document.add(new Paragraph("\n"));
        document.add(table2);
        document.add(new Paragraph("\n\n\nMerci d'avoir réservé via notre application\n\n\n\n\n\n").setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n\n\n\n\n\nBlasti application 2023").setTextAlignment(TextAlignment.CENTER));

        document.close();
        Toast.makeText(this, "pdf créé", Toast.LENGTH_SHORT).show();

        return file;
    }
}