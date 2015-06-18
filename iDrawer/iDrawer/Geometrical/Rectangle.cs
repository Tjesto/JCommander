﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using iDrawer.Plugins;
using System.Drawing;
using System.Windows.Forms;

namespace Geometrical
{
    public class Rectangle : DrawingTool
    {
        private List<OnToolChangedListener> listeners = new List<OnToolChangedListener>();
        protected int firstX, firstY;

        public string getName()
        {
            return "Rectangle";
        }

        public void draw(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            //do nothing            
        }

        public void onDrawStarted(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            firstX = startX;
            firstY = startY;
        }

        public void onDrawFinished(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            int beginX = x < firstX ? x : firstX;
            int beginY = y < firstY ? y : firstY;
            int endX = x >= firstX ? x : firstX;
            int endY = y >= firstY ? y : firstY;
            g.DrawRectangle(new Pen(b, width), beginX - width/2, beginY - width/2, endX - beginX, endY - beginY);
            firstX = 0;
            firstY = 0;
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

    public class FilledRectangle : DrawingTool
    {
        private List<OnToolChangedListener> listeners = new List<OnToolChangedListener>();
        protected int firstX, firstY;

        public string getName()
        {
            return "Filled Rectangle";
        }

        public void draw(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            //do nothing            
        }

        public void onDrawStarted(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            firstX = startX;
            firstY = startY;
        }

        public void onDrawFinished(Pen p, Brush b, Graphics g, int startX, int startY, int x, int y, int width)
        {
            int beginX = x < firstX ? x : firstX;
            int beginY = y < firstY ? y : firstY;
            int endX = x >= firstX ? x : firstX;
            int endY = y >= firstY ? y : firstY;
            g.DrawRectangle(new Pen(b, width), beginX - width/2, beginY - width/2, endX - beginX, endY - beginY);
            g.FillRectangle(b, beginX - width / 2, beginY - width / 2, endX - beginX, endY - beginY);
            firstX = 0;
            firstY = 0;
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
