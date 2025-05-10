package cz.sa.tripfinder;

import cz.sa.tripfinder.configuration.TripFinderConfig;
import cz.sa.tripfinder.mappers.TripMapper;
import cz.sa.tripfinder.model.TripFuzzEvaluationDTO;
import cz.sa.tripfinder.model.TripFuzzyDTO;
import cz.sa.tripfinder.model.TripPureDTO;
import cz.sa.tripfinder.services.MappingHelper;
import cz.sa.tripfinder.services.JFuzzService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class FuzzyTripEvaluation {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TripFinderConfig.class);
        TripMapper mapper = context.getBean(TripMapper.class);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Napište ID zájezdu: \n");
        Long tripId = scanner.nextLong();

        System.out.print("Napište nejvyšší cenu zájezdu: \n");
        Float maxPrice = scanner.nextFloat();

        System.out.print("Napište nejnižší cenu zájezdu: \n");
        Float minPrice = scanner.nextFloat();

        TripPureDTO pureTrip = mapper.getPureTripById(tripId);

        System.out.print("Destinace zájezdu je " + pureTrip.getLocation() + ". Vyhodnoďte preferenci destinace: \n");
        System.out.print("Vysoce preferovaná = 1 \n");
        System.out.print("Preferovaná = 2 \n");
        System.out.print("Bez preference = 3 \n");
        int locationPref = scanner.nextInt();

        System.out.print("Místo odletu je " + pureTrip.getAirport() + ". Vyhodnoďte preferenci letiště: \n");
        System.out.print("Vysoce preferované = 1 \n");
        System.out.print("Preferované = 2 \n");
        System.out.print("Bez preference = 3 \n");
        int airportPref = scanner.nextInt();

        System.out.print("Stravovací balíček zájezdu je  " + pureTrip.getFoodPackage() + ". Vyhodnoďte preferenci balíčku: \n");
        System.out.print("Nízká = 1 \n");
        System.out.print("Střední = 2 \n");
        System.out.print("Vysoká = 3 \n");
        int foodPref = scanner.nextInt();

        JFuzzService jFuzzService = new JFuzzService();
        TripFuzzEvaluationDTO tripFuzzEvaluation = jFuzzService.evaluate(
                MappingHelper.EQUIPMENT_MAP.get(pureTrip.isFamilyFriendly()),
                MappingHelper.EQUIPMENT_MAP.get(pureTrip.isInternet()),
                MappingHelper.EQUIPMENT_MAP.get(pureTrip.isSwimmingPool()),
                pureTrip.getStars(),
                pureTrip.getAverageRating(),
                locationPref,
                MappingHelper.BEACH_DIST_MAP.get(pureTrip.getBeachDist()),
                airportPref,
                foodPref,
                MappingHelper.calculatePriceValue(minPrice, maxPrice, pureTrip.getPrice()),
                true);
        System.out.println(new TripFuzzyDTO(pureTrip, tripFuzzEvaluation));

        scanner.close();
        context.close();
    }
}
