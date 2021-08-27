package cdm.product.asset.calculation.functions;

import cdm.base.math.*;
import cdm.base.math.metafields.ReferenceWithMetaQuantity;
import cdm.observable.asset.Money;
import cdm.product.asset.InterestRatePayout;
import cdm.product.common.schedule.CalculationPeriod;
import cdm.product.common.schedule.CalculationPeriodBase;
import cdm.product.common.schedule.NonNegativeQuantitySchedule;
import cdm.product.common.settlement.ResolvablePayoutQuantity;
import com.google.inject.Inject;
import com.rosetta.model.lib.records.DateImpl;
import com.rosetta.model.metafields.FieldWithMetaString;
import org.isda.cdm.functions.AbstractFunctionTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LookupNotionalAmountTest extends AbstractFunctionTest {

	@Inject
	private LookupNotionalAmount func;

	@Test
	void shouldLookupValue() {
		InterestRatePayout interestRatePayout = InterestRatePayout.builder()
				.setPayoutQuantity(initNotionalSchedule())
				.build();

		CalculationPeriodBase dec1_2020 = CalculationPeriod.builder()
				.setAdjustedStartDate(DateImpl.of(2020, 12, 10))
				.setAdjustedEndDate(DateImpl.of(2021, 3, 10)).build();
		CalculationPeriodBase mar1 = CalculationPeriod.builder()
				.setAdjustedStartDate(DateImpl.of(2021, 3, 10))
				.setAdjustedEndDate(DateImpl.of(2021, 6, 10)).build();
		CalculationPeriodBase jun1 = CalculationPeriod.builder()
				.setAdjustedStartDate(DateImpl.of(2021, 6, 10))
				.setAdjustedEndDate(DateImpl.of(2021, 9, 10)).build();
		CalculationPeriodBase sep1 = CalculationPeriod.builder()
				.setAdjustedStartDate(DateImpl.of(2021, 9, 10))
				.setAdjustedEndDate(DateImpl.of(2021, 12, 10)).build();
		CalculationPeriodBase dec1 = CalculationPeriod.builder()
				.setAdjustedStartDate(DateImpl.of(2021, 12, 10))
				.setAdjustedEndDate(DateImpl.of(2022, 3, 10)).build();

		Money nineMillion = Money.builder()
				.setMultiplierUnit(UnitType.builder().setCurrencyValue("USD"))
				.setMultiplier(BigDecimal.valueOf(9_000_000));
		Money tenMillion = Money.builder()
				.setMultiplierUnit(UnitType.builder().setCurrencyValue("USD"))
				.setMultiplier(BigDecimal.valueOf(10_000_000));
		Money elevenMillion = Money.builder()
				.setMultiplierUnit(UnitType.builder().setCurrencyValue("USD"))
				.setMultiplier(BigDecimal.valueOf(11_000_000));
		Money twelveMillion = Money.builder()
				.setMultiplierUnit(UnitType.builder().setCurrencyValue("USD"))
				.setMultiplier(BigDecimal.valueOf(12_000_000));
		Money thirteenMillion = Money.builder()
				.setMultiplierUnit(UnitType.builder().setCurrencyValue("USD"))
				.setMultiplier(BigDecimal.valueOf(13_000_000));

		assertEquals(thirteenMillion, func.evaluate(interestRatePayout, dec1));
		assertEquals(elevenMillion, func.evaluate(interestRatePayout, jun1));
		assertEquals(nineMillion, func.evaluate(interestRatePayout, dec1_2020));
		assertEquals(tenMillion, func.evaluate(interestRatePayout, mar1));
		assertEquals(twelveMillion, func.evaluate(interestRatePayout, sep1));
	}

	public static ResolvablePayoutQuantity initNotionalSchedule() {
		return ResolvablePayoutQuantity.builder()
				.setQuantitySchedule(NonNegativeQuantitySchedule.builder()
						.setInitialQuantity(ReferenceWithMetaQuantity.builder()
								.setValue(Quantity.builder()
										.setMultiplier(BigDecimal.valueOf(9_000_000))
										.setMultiplierUnit(UnitType.builder()
												.setCurrency(FieldWithMetaString.builder()
														.setValue("USD").build())
												.build())
										.build())
								.build())
						.setStepSchedule(NonNegativeStepSchedule.builder()
								.addStep(NonNegativeStep.builder()
										.setStepDate(DateImpl.of(2021, 3, 10))
										.setStepValue(BigDecimal.valueOf(10_000_000))
										.build())
								.addStep(NonNegativeStep.builder()
										.setStepDate(DateImpl.of(2021, 6, 10))
										.setStepValue(BigDecimal.valueOf(11_000_000))
										.build())
								.addStep(NonNegativeStep.builder()
										.setStepDate(DateImpl.of(2021, 9, 10))
										.setStepValue(BigDecimal.valueOf(12_000_000))
										.build())
								.addStep(NonNegativeStep.builder()
										.setStepDate(DateImpl.of(2021, 12, 10))
										.setStepValue(BigDecimal.valueOf(13_000_000))
										.build())
								.build())
						.build())
				.build();

	}
}
