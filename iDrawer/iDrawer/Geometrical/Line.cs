using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;
using iDrawer.Plugins;

namespace Geometrical
{
    public class Line : DrawingTool
    {
        private List<OnToolChangedListener> listeners = new List<OnToolChangedListener>();
        protected int firstX, firstY;

        public string getName()
        {
            return "Line";
        }

        public void draw(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill)
        {
            //do nothing            
        }

        public void onDrawStarted(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill)
        {
            firstX = startX;
            firstY = startY;
        }

        public void onDrawFinished(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width, Brush fill)
        {            
            g.DrawLine(new Pen(b, width), firstX - width/2, firstY - width/2, x - width/2, y - width/2);                        
        }

        public ToolStripItem getItem()
        {
            ToolStripItem pluginItem = new ToolStripButton();
            pluginItem.Text = getName();
            pluginItem.Name = getName();
            pluginItem.Click += onClick;
            return pluginItem;
        }

        private void onClick(object sender, EventArgs e)
        {
            foreach (OnToolChangedListener listener in listeners)
            {
                listener.notifyChanged(this);
            }
        }

        public Base getInstance()
        {
            return this;
        }

        public void registerOnToolChangedListener(OnToolChangedListener listener)
        {
            listeners.Add(listener);
        }
    }
}
