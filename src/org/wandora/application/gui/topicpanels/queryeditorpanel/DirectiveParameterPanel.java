/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2014 Wandora Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.wandora.application.gui.topicpanels.queryeditorpanel;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.wandora.application.gui.topicpanels.queryeditorpanel.ConnectorAnchor.Direction;
import org.wandora.query2.Directive;
import org.wandora.query2.DirectiveUIHints;

/**
 *
 * @author olli
 */


public class DirectiveParameterPanel extends AbstractTypePanel {

    protected ConnectorAnchor connectorAnchor;
    protected Class<? extends Directive> directiveType;
    
    /**
     * Creates new form DirectiveParameterPanel
     */
    public DirectiveParameterPanel() {
        initComponents();
        
        connectorAnchor=new ComponentConnectorAnchor(directiveAnchor,Direction.RIGHT,true,false);
        
        DnDTools.addDropTargetHandler(directiveAnchor, DnDTools.directiveHintsDataFlavor, 
                new DnDTools.DropTargetCallback<DirectiveUIHints>() {

            @Override
            public boolean callback(JComponent component, DirectiveUIHints hints, TransferHandler.TransferSupport support) {
                if(directiveType!=null && !directiveType.isAssignableFrom(hints.getClass())) return false;
                
                DirectivePanel directivePanel=getDirectivePanel();
                QueryEditorComponent editor=directivePanel.getEditor();

                DirectivePanel panel=editor.addDirective(hints);
                Point point=support.getDropLocation().getDropPoint();
                Rectangle rect=directivePanel.getBounds();
                panel.setBounds(rect.x+rect.width+10,rect.y,panel.getWidth(),panel.getHeight());

                connectorAnchor.setFrom(panel.getConnectorAnchor());

                return true;                
            }
        });
        
        DnDTools.addDropTargetHandler(directiveAnchor, DnDTools.directivePanelDataFlavor, 
                new DnDTools.DropTargetCallback<DirectivePanel>() {

            @Override
            public boolean callback(JComponent component, DirectivePanel o, TransferHandler.TransferSupport support) {
                if(o==getDirectivePanel()) return false;
                connectorAnchor.setFrom(o.getConnectorAnchor());
                return true;
            }
        });        
        
    }

    @Override
    public void disconnect() {
        super.disconnect(); 
        connectorAnchor.setFrom(null);
    }


    
    public void setDirectiveType(Class<? extends Directive> cls){
        directiveType=cls;
    }

    public void setLabel(String label){
        parameterLabel.setText(label);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        parameterLabel = new javax.swing.JLabel();
        directiveAnchor = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        parameterLabel.setText("Label");
        add(parameterLabel, new java.awt.GridBagConstraints());

        directiveAnchor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        directiveAnchor.setText("*");
        directiveAnchor.setMaximumSize(new java.awt.Dimension(20, 20));
        directiveAnchor.setMinimumSize(new java.awt.Dimension(20, 20));
        directiveAnchor.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        add(directiveAnchor, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel directiveAnchor;
    private javax.swing.JLabel parameterLabel;
    // End of variables declaration//GEN-END:variables
}
