﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using iDrawer.Plugins;
using System.Drawing;
using System.Windows.Forms;

namespace Pen
{
    public class Pen : DrawingTool
    {
        private List<OnToolChangedListener> listeners = new List<OnToolChangedListener>();

        public string getName()
        {
            return "Pen";
        }

        public void draw(System.Drawing.Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            g.DrawEllipse(p, x, y, width, width);
            g.FillEllipse(b, x, y, width, width);
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
