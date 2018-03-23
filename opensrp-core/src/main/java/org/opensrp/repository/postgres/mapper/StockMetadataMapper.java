package org.opensrp.repository.postgres.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.StockMetadata;
import org.opensrp.domain.postgres.StockMetadataExample;
import org.apache.ibatis.session.RowBounds;

public interface StockMetadataMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	long countByExample(StockMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int deleteByExample(StockMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int insert(StockMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int insertSelective(StockMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	List<StockMetadata> selectByExample(StockMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	StockMetadata selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int updateByExampleSelective(@Param("record") StockMetadata record, @Param("example") StockMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int updateByExample(@Param("record") StockMetadata record, @Param("example") StockMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int updateByPrimaryKeySelective(StockMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.stock_metadata
	 * @mbg.generated  Fri Mar 23 15:56:38 EAT 2018
	 */
	int updateByPrimaryKey(StockMetadata record);
}