package resources;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class generateGraph extends ApplicationFrame{
   public generateGraph( String applicationTitle , String chartTitle, DefaultCategoryDataset createDataset ){
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Executions","Time Execution",
         createDataset(createDataset),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 1300 , 600 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset(DefaultCategoryDataset dataset){
      return dataset;
   }
}
