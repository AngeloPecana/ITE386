import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the image file path: ");
        String imagePath = scanner.nextLine();

        String detectedDisease = callImageRecognitionSystem(imagePath);
        String report = generateReport(detectedDisease);

        System.out.println("\nCrop Disease Detection Report:");
        System.out.println(report);

        scanner.close();
    }

    public static String callImageRecognitionSystem(String imagePath) throws IOException {
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            ByteString imgBytes = ByteString.readFrom(new FileInputStream(new File(imagePath)));
            Image image = Image.newBuilder().setContent(imgBytes).build();
            Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feature)
                    .setImage(image)
                    .build();

            List<AnnotateImageResponse> responses = vision.batchAnnotateImages(List.of(request)).getResponsesList();
            for (AnnotateImageResponse res : responses) {
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    String label = annotation.getDescription();
                    if (label.contains("blight") || label.contains("mildew") || label.contains("fungus")) {
                        return label;
                    }
                }
            }
        }
        return "Unknown Disease";
    }

    public static String generateReport(String detectedDisease) {
        StringBuilder report = new StringBuilder();
        report.append("=======================================\n");
        report.append("CROP DISEASE DETECTION REPORT\n");
        report.append("=======================================\n");
        report.append("Detected Disease: ").append(detectedDisease).append("\n\n");

        if (detectedDisease.toLowerCase().contains("blight")) {
            report.append("🔹 Treatment: Apply copper-based fungicides.\n");
            report.append("🔹 Prevention: Use disease-resistant crop varieties.\n");
        } else if (detectedDisease.toLowerCase().contains("mildew")) {
            report.append("🔹 Treatment: Use sulfur-based fungicides.\n");
            report.append("🔹 Prevention: Ensure proper air circulation in crops.\n");
        } else {
            report.append("🔹 No specific disease detected. Consult an expert for further analysis.\n");
        }

        report.append("=======================================\n");
        return report.toString();
    }
}
