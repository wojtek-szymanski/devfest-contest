package pl.allegro.devfest;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BestDealAdvisor {

    private final Collection<Auction> auctions;

    public BestDealAdvisor(Collection<Auction> auctions) {
        this.auctions = Collections.unmodifiableCollection(auctions);
    }

    public Optional<BestDeal> findBestDeal(DealCriteria dealRequirements) {
        Set<GoodType> expectedGoodTypes = dealRequirements.getGoodTypes();
        Map<GoodType, List<Auction>> auctionsByType = auctions.stream().
                filter(t -> expectedGoodTypes.contains(t.getGoodType())).
                collect(Collectors.groupingBy(Auction::getGoodType));

        Set<GoodType> actualGoodTypes = auctionsByType.keySet();
        if (!actualGoodTypes.containsAll(expectedGoodTypes)) {
            return Optional.empty();
        }


        List<Auction> result = new ArrayList<>();
        auctionsByType.forEach(new BiConsumer<GoodType, List<Auction>>() {
            @Override
            public void accept(GoodType goodType, List<Auction> auctions) {
                Optional<Auction> min = auctions.stream().min(Comparator.comparing(price -> price.getPrice()));
                result.add(min.get());
            }
        });

        BestDeal bestDeal = new BestDeal(result);
        BigDecimal totalPrice = bestDeal.getTotalPrice();
        BigDecimal budget = dealRequirements.getBudget();

        return totalPrice.compareTo(budget) <= 0 ? Optional.of(bestDeal) : Optional.empty();
    }
}
