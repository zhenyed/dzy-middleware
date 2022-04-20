package io.zhenye.sharding.algorithm;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

@Slf4j
public class YearShardingAlgorithm<T extends Comparable<?>> implements ComplexKeysShardingAlgorithm<T> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<T> shardingValues) {
        List<String> result = Lists.newArrayList(availableTargetNames);
        String firstTableName = result.get(0);

        // 处理精确条件
        Map<String, Collection<T>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        Collection<Date> createTimeList = (Collection<Date>) columnNameAndShardingValuesMap.get("create_time");

        // 移除不符合条件的时间
        if (CollectionUtils.isNotEmpty(createTimeList)) {
            for (Date createTime : createTimeList) {
                result.removeIf(i -> !i.endsWith("_" + DateUtil.year(createTime)));
            }
        }

        // 处理范围条件
        Map<String, Range<T>> columnNameAndRangeValuesMap = shardingValues.getColumnNameAndRangeValuesMap();
        Range<Date> range = (Range<Date>) columnNameAndRangeValuesMap.get("create_time");
        if (range != null) {
            // 2.1.移除小于最小值
            if (range.hasLowerBound()) {
                result.removeIf(i -> Integer.parseInt(ReUtil.getGroup0("_[0-9]{4}", i).substring(1)) < DateUtil.year(range.lowerEndpoint()));
            }
            // 2.2.移除大于最大值
            if (range.hasUpperBound()) {
                result.removeIf(i -> Integer.parseInt(ReUtil.getGroup0("_[0-9]{4}", i).substring(1)) > DateUtil.year(range.upperEndpoint()));
            }
        }

        // 避免当所有表都不符合条件而出现报错，随便返回一个表
        if (CollectionUtils.isEmpty(result)) {
            return Collections.singletonList(firstTableName);
        }
        return result;
    }

}